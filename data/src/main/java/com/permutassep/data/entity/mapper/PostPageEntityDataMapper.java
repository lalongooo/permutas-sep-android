package com.permutassep.data.entity.mapper;

import com.permutassep.data.entity.PostPageEntity;
import com.permutassep.domain.PostPage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link PostPageEntity} (in the data layer) to {@link PostPage} in the
 * domain layer.
 */
@Singleton
public class PostPageEntityDataMapper {


    PostEntityDataMapper postEntityDataMapper;

    @Inject
    public PostPageEntityDataMapper(PostEntityDataMapper postEntityDataMapper) {
        this.postEntityDataMapper = postEntityDataMapper;
    }

    /**
     * Transform a {@link PostPageEntity} into an {@link PostPage}.
     *
     * @param postPageEntity Object to be transformed.
     * @return {@link PostPage} if valid {@link PostPageEntity} otherwise null.
     */
    public PostPage transform(PostPageEntity postPageEntity) {
        PostPage postPage = null;
        if (postPageEntity != null) {
            postPage = new PostPage();
            postPage.setCount(postPageEntity.getCount());
            postPage.setNext(postPageEntity.getNext());
            postPage.setPrevious(postPageEntity.getPrevious());
            postPage.setResults(postEntityDataMapper.transform(postPageEntity.getResults()));
        }

        return postPage;
    }

    /**
     * Transform a List of {@link PostPageEntity} into a Collection of {@link PostPage}.
     *
     * @param postPageEntityCollection Object Collection to be transformed.
     * @return {@link PostPage} if valid {@link PostPageEntity} otherwise null.
     */
    public List<PostPage> transform(Collection<PostPageEntity> postPageEntityCollection) {
        List<PostPage> postPageList = new ArrayList<>();
        PostPage postPage;
        for (PostPageEntity postPageEntity : postPageEntityCollection) {
            postPage = transform(postPageEntity);
            if (postPage != null) {
                postPageList.add(postPage);
            }
        }

        return postPageList;
    }
}
