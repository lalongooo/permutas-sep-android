package com.permutassep.presentation.view.wizard.model;

import android.content.Context;

public class PermutaSepWizardModel extends AbstractWizardModel {

    public PermutaSepWizardModel(Context context) {
		super(context);
	}

    public static final String POST_TEXT_KEY = "post_text_page_key";
    public static final String CONTACT_INFO_KEY = "contact_info_page_key";
    public static final String CITY_FROM_KEY = "city_from_page_key";
    public static final String CITY_TO_KEY = "city_to_page_key";
    public static final String POSITION_TYPE_KEY = "position_type_page_key";
    public static final String WORKDAY_TYPE_KEY = "workday_type_page_key";
    public static final String TEACHING_CAREER_KEY = "teaching_career_page_key";
    public static final String ACADEMIC_LEVEL_KEY = "academic_level_page_key";

	@Override
	protected PageList onNewRootPageList() {
		return new PageList(
//                new ProfessorContactInfoPage(this, "Tu información").setRequired(true).setKey(CONTACT_INFO_KEY),
                new ProfessorCityFromPage(this, "Tu ciudad de origen").setRequired(true).setKey(CITY_FROM_KEY),
                new ProfessorCityToPage(this, "Tu lugar deseado").setRequired(true).setKey(CITY_TO_KEY),
                new SingleFixedChoicePage(this, "Nivel académico").setChoices("Educación Especial", "Pre-escolar", "Primaria", "Secundaria", "Tele-secundaria", "Medio superior","Administrativo", "Intendencia").setRequired(true).setKey(ACADEMIC_LEVEL_KEY),
                new SingleFixedChoicePage(this, "Tipo de plaza").setChoices("Estatal", "Federal").setRequired(true).setKey(POSITION_TYPE_KEY),
                new SingleFixedChoicePage(this, "Tipo de jornada").setChoices("Jornada Regular", "Jornada ampliada", "Tiempo completo").setRequired(true).setKey(WORKDAY_TYPE_KEY),
                new SingleFixedChoicePage(this, "Carrera magisterial").setChoices("Si", "No").setRequired(true).setKey(TEACHING_CAREER_KEY),
                new PostTextPage(this, "Información adicional").setRequired(true).setKey(POST_TEXT_KEY)
				);
	}
}