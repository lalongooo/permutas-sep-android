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
 * Mapper class used to transform {@link PostEntity} (in the data layer) to {@link User} in the
 * domain layer.
 */
@Singleton
public class PostEntityDataMapper {

    UserEntityDataMapper userEntityDataMapper;

    @Inject
    public PostEntityDataMapper(UserEntityDataMapper userEntityDataMapper) {
        this.userEntityDataMapper = userEntityDataMapper;
    }

    /**
     * Transform a {@link PostEntity} into an {@link User}.
     *
     * @param postEntity Object to be transformed.
     * @return {@link User} if valid {@link PostEntity} otherwise null.
     */
    public Post transform(PostEntity postEntity) {
        Post post = null;
        if (postEntity != null) {
            post.setCityFrom(postEntity.getCityFrom());
            post.setCityTo(postEntity.getCityTo());
            post.setLatFrom(postEntity.getLatFrom());
            post.setLatTo(postEntity.getLatTo());
            post.setLonFrom(postEntity.getLonFrom());
            post.setLonTo(postEntity.getLonTo());
            post.setmAcademicLevel(postEntity.getAcademicLevel());
            post.setmIsTeachingCareer(postEntity.isTeachingCareer());
            post.setmPositionType(postEntity.getPositionType());
            post.setmPostDate(postEntity.getPostDate());
            post.setUser(userEntityDataMapper.transform(postEntity.getUser()));
            post.setmWorkdayType(postEntity.getWorkdayType());
            post.setPostText(postEntity.getPostText());
            post.setStateFrom(postEntity.getStateFrom());
            post.setStateTo(postEntity.getStateTo());
            post.setTownFrom(postEntity.getTownFrom());
            post.setTownTo(postEntity.getTownTo());
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
}
