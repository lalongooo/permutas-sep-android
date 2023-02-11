package com.permutassep.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.afollestad.materialdialogs.MaterialDialog
import com.facebook.*
import com.facebook.CallbackManager.Factory.create
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.lalongooo.permutassep.BuildConfig
import com.lalongooo.permutassep.R
import com.permutassep.presentation.interfaces.LoginCompleteListener
import com.permutassep.presentation.internal.di.components.ApplicationComponent
import com.permutassep.presentation.internal.di.components.AuthenticationComponent
import com.permutassep.presentation.internal.di.components.DaggerAuthenticationComponent
import com.permutassep.presentation.internal.di.modules.AuthenticationModule
import com.permutassep.presentation.model.UserModel
import com.permutassep.presentation.presenter.LoginPresenter
import com.permutassep.presentation.view.LoginView
import com.throrinstudio.android.common.libs.validator.Form
import com.throrinstudio.android.common.libs.validator.Validate
import com.throrinstudio.android.common.libs.validator.validator.EmailValidator
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator
import java.util.*
import javax.inject.Inject

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
class FragmentLogin
/**
 * Empty constructor
 */
    : BaseFragment(), LoginView {
    /**
     * UI elements
     */
    @BindView(R.id.etName)
    lateinit var etName: EditText

    @BindView(R.id.etPassword)
    lateinit var etPassword: EditText

    @BindView(R.id.btnFbLogin)
    lateinit var facebookLoginButton: LoginButton

    @BindView(R.id.tvForgotPassword)
    lateinit var tvForgotPassword: TextView

    @JvmField
    @Inject
    var loginPresenter: LoginPresenter? = null
    private var loginCompleteListener: LoginCompleteListener? = null
    private var progressDialog: MaterialDialog? = null
    private lateinit var authenticationComponent: AuthenticationComponent
    private lateinit var callbackManager: CallbackManager
    private lateinit var editTextEmail: EditText
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(R.layout.ca_fragment_login, container, false)
        ButterKnife.bind(this, fragmentView)
        setUpFacebookLoginButton()
        firebaseAuth = FirebaseAuth.getInstance()
        val actionBar = activity!!.actionBar
        actionBar?.hide()
        return fragmentView
    }

    private fun setUpFacebookLoginButton() {
        callbackManager = create()
        facebookLoginButton.setReadPermissions(Arrays.asList("email"))
        facebookLoginButton.setFragment(this)
        facebookLoginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                showLoading()
                getFacebookEmail(result.accessToken)
            }

            override fun onCancel() {
                showError(getString(R.string.app_login_fb_dlg_on_cancel))
            }

            override fun onError(error: FacebookException) {
                showError(getString(R.string.app_login_fb_dlg_on_error))
            }
        })
    }

    private fun getFacebookEmail(accessToken: AccessToken) {
        val callback = GraphRequest.GraphJSONObjectCallback { jsonObject, response ->
            hideLoading()
            try {
                val email = jsonObject?.getString("email")!!
                handleFacebookAccessToken(accessToken, email)
            } catch (e: Exception) {

                val scale = resources.displayMetrics.density
                val dpAsPixels = (15 * scale + 0.5f).toInt()
                editTextEmail = EditText(requireContext())
                editTextEmail.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels)

                val alert = AlertDialog.Builder(activity)
                alert.setView(editTextEmail)
                alert.setCancelable(false)
                alert.setTitle(R.string.app_login_fb_dlg_missing_email_title)
                alert.setMessage(R.string.app_login_fb_dlg_missing_email_text)
                alert.setPositiveButton(R.string.accept) { dialog, _ ->
                    if (EmailValidator(activity).isValid(editTextEmail.text.toString())) {
                        val email = editTextEmail.text.toString()
                        handleFacebookAccessToken(accessToken, email)
                    } else {
                        LoginManager.getInstance().logOut()
                        showError(getString(R.string.app_login_fb_dlg_wrong_email))
                    }
                }
                alert.setNegativeButton(R.string.cancel) { dialog, whichButton ->
                    LoginManager.getInstance().logOut()
                    showError(getString(R.string.app_login_fb_dlg_on_cancel))
                }
                alert.show()
            }
        }

        val graphRequest = GraphRequest.newMeRequest(accessToken, callback)
        val parameters = Bundle().apply { putString("fields", "email") }
        graphRequest.parameters = parameters
        graphRequest.executeAsync()
    }

    private fun handleFacebookAccessToken(token: AccessToken, email: String) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                updateUserEmail(email)
            } else {
                // no-op
            }
        }
    }

    private fun updateUserEmail(email: String) {
        firebaseAuth.currentUser?.let { firebaseUser ->
            firebaseUser.updateEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            requireActivity().supportFragmentManager
                                    .popBackStackImmediate(null, POP_BACK_STACK_INCLUSIVE)
                            navigationListener.onNextFragment(FragmentPagedNewsFeed::class.java)
                        }
                    }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginCompleteListener = activity as LoginCompleteListener?
    }

    @OnClick(R.id.btnLogin)
    fun onBtnLoginClick() {
        /**
         * Add validations of the EditText's
         */
        val vName = Validate(etName)
        vName.addValidator(EmailValidator(activity))
        val vPassword = Validate(etPassword)
        vPassword.addValidator(NotEmptyValidator(activity))
        val form = Form()
        form.addValidates(vName, vPassword)
        if (form.isValid) {
            val user = etName!!.text.toString()
            val password = etPassword!!.text.toString()
            initializeInjector(user, password)
            loginPresenter!!.login()
        }
    }

    @OnClick(R.id.tvForgotPassword)
    fun onForgotPasswordClick() {
        MaterialDialog.Builder(activity!!)
                .title(R.string.password_reset_dlg_title)
                .content(R.string.password_reset_dlg_msg)
                .inputRangeRes(6, 200, R.color.md_red_100)
                .inputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                .input(R.string.password_reset_dlg_input_hint, 0, false) { dialog, input ->
                    initializeInjector(input.toString(), BuildConfig.com_permutassep_fb_login_dummy_password)
                    loginPresenter!!.validateEmail(input.toString())
                }.show()
    }

    private fun initializeInjector(user: String, password: String) {
        authenticationComponent = DaggerAuthenticationComponent.builder()
                .applicationComponent(getComponent(ApplicationComponent::class.java))
                .activityModule((activity as BaseActivity?)!!.activityModule)
                .authenticationModule(AuthenticationModule(user, password))
                .build()
        authenticationComponent.inject(this)
        loginPresenter!!.setView(this)
    }

    /**
     * Methods from the [LoginView] interface
     */
    override fun authorizeUser(userModel: UserModel) {
        hideKeyboard()
        activity!!.supportFragmentManager.popBackStackImmediate(null, POP_BACK_STACK_INCLUSIVE)
        navigationListener.onNextFragment(FragmentPagedNewsFeed::class.java)
        loginCompleteListener!!.onLoginComplete(userModel)
    }

    override fun showLoading() {
        progressDialog = MaterialDialog.Builder(activity!!)
                .title(R.string.app_login_dlg_login_title)
                .content(R.string.app_login_dlg_login_logging_in)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .cancelable(false)
                .show()
    }

    override fun notRegisteredUser() {
        MaterialDialog.Builder(activity!!)
                .title(R.string.password_reset_dlg_title)
                .content(R.string.new_password_capture_email_wrong)
                .positiveText(R.string.accept)
                .show()
    }

    override fun showLoadingValidateUser() {
        progressDialog = MaterialDialog.Builder(activity!!)
                .title(R.string.please_wait)
                .content(R.string.new_password_capture_email_loading)
                .progress(true, 0)
                .cancelable(false)
                .progressIndeterminateStyle(false)
                .show()
    }

    override fun performPasswordReset(email: String) {
        loginPresenter!!.resetPassword(email)
    }

    override fun passwordResetRequestSent(email: String) {
        MaterialDialog.Builder(activity!!)
                .title(R.string.password_reset_dlg_title)
                .content(String.format(getString(R.string.password_reset_email_sent_msg), email))
                .positiveText(R.string.accept)
                .show()
    }

    override fun hideLoading() {
        if (progressDialog != null) progressDialog!!.dismiss()
    }

    override fun showRetry() {}
    override fun hideRetry() {}
    override fun showError(message: String) {
        LoginManager.getInstance().logOut()
        MaterialDialog.Builder(activity!!)
                .title(R.string.app_login_dlg_login_title)
                .content(message)
                .positiveText(R.string.accept)
                .show()
    }

    companion object {
        /**
         * A static method to create a new instance of the [FragmentLogin] class
         *
         * @return An instance of [FragmentLogin]
         */
        @JvmStatic
        fun newInstance(): FragmentLogin {
            return FragmentLogin()
        }
    }
}