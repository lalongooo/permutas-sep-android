package com.permutassep.data.entity.mapper;

import com.permutassep.data.entity.PostEntity;
import com.permutassep.domain.Post;
import com.permutassep.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform a {@link PostEntity} of the
 * data layer to a {@link User} in the domain layer.
 */
@Singleton
public class PostEntityDataMapper {

    UserEntityDataMapper userEntityDataMapper;

    @Inject
    public PostEntityDataMapper(UserEntityDataMapper userEntityDataMapper) {
        this.userEntityDataMapper = userEntityDataMapper;
    }

    /**
     * Transform a {@link PostEntity} into an {@link Post}.
     *
     * @param postEntity Object to be transformed.
     * @return {@link Post} if valid {@link PostEntity} otherwise null.
     */
    public Post transform(PostEntity postEntity) {
        Post post = null;
        if (postEntity != null) {
            post = new Post();
            post.setAcademicLevel(postEntity.getAcademicLevel());
            post.setCityFrom(postEntity.getCityFrom());
            post.setCityTo(postEntity.getCityTo());
            post.setId(postEntity.getId());
            post.setIsTeachingCareer(postEntity.isTeachingCareer());
            post.setLatFrom(postEntity.getLatFrom());
            post.setLatTo(postEntity.getLatTo());
            post.setLonFrom(postEntity.getLonFrom());
            post.setLonTo(postEntity.getLonTo());
            post.setPositionType(postEntity.getPositionType());
            post.setPostDate(postEntity.getPostDate());
            post.setPostText(postEntity.getPostText());
            post.setStateFrom(postEntity.getStateFrom());
            post.setStateFromCode(postEntity.getStateFromCode());
            post.setStateTo(postEntity.getStateTo());
            post.setStateToCode(postEntity.getStateToCode());
            post.setTownFrom(postEntity.getTownFrom());
            post.setTownTo(postEntity.getTownTo());
            post.setUser(userEntityDataMapper.transform(postEntity.getUser()));
            post.setWorkdayType(postEntity.getWorkdayType());
        }

        return post;
    }

    /**
     * Transform a List of {@link PostEntity} into a Collection of {@link User}.
     *
     * @param postEntityCollection Object Collection to be transformed.
     * @return {@link User} if valid {@link PostEntity} otherwise null.
     */
    public List<Post> transform(Collection<PostEntity> postEntityCollection) {
        List<Post> postList = new ArrayList<>();
        Post post;
        for (PostEntity postEntity : postEntityCollection) {
            post = transform(postEntity);
            if (post != null) {
                postList.add(post);
            }
        }
        return postList;
    }


    /**
     * Transform a {@link Post} into an {@link PostEntity}.
     *
     * @param post Object to be transformed.
     * @return {@link PostEntity} if valid {@link Post} otherwise null.
     */
    public PostEntity transform(Post post) {
        PostEntity postEntity = null;
        if (post != null) {
            postEntity = new PostEntity();
            postEntity.setId(post.getId());
            postEntity.setUser(userEntityDataMapper.transform(post.getUser()));
            postEntity.setPostText(post.getPostText());
            postEntity.setStateFrom(post.getStateFrom());
            postEntity.setCityFrom(post.getCityFrom());
            postEntity.setTownFrom(post.getTownFrom());
            postEntity.setLatFrom(post.getLatFrom());
            postEntity.setLonFrom(post.getLonFrom());
            postEntity.setStateTo(post.getStateTo());
            postEntity.setCityTo(post.getCityTo());
            postEntity.setTownTo(post.getTownTo());
            postEntity.setLatTo(post.getLatTo());
            postEntity.setLonTo(post.getLonTo());
            postEntity.setPositionType(post.getPositionType());
            postEntity.setWorkdayType(post.getWorkdayType());
            postEntity.setIsTeachingCareer(post.isTeachingCareer());
            postEntity.setPostDate(post.getPostDate());
            postEntity.setAcademicLevel(post.getAcademicLevel());
            postEntity.setStateFromCode(post.getStateFromCode());
            postEntity.setStateToCode(post.getStateToCode());
        }

        return postEntity;
    }
}
