/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.permutassep.presentation.mapper;

import com.permutassep.domain.PostPage;
import com.permutassep.presentation.internal.di.PerActivity;
import com.permutassep.presentation.model.PostPageModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link PostPage} (in the domain layer)
 * to {@link PostPageModel} in the presentation layer.
 */
@PerActivity
public class PostPageModelDataMapper {

    private final PostModelDataMapper postModelDataMapper;

    @Inject
    public PostPageModelDataMapper(PostModelDataMapper postModelDataMapper) {
        this.postModelDataMapper = postModelDataMapper;
    }

    /**
     * Transform a {@link PostPage} into an {@link PostPageModel}.
     *
     * @param postPage Object to be transformed.
     * @return {@link PostPageModel}.
     */
    public PostPageModel transform(PostPage postPage) {
        if (postPage == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }

        PostPageModel postPageModel = new PostPageModel();
        postPageModel.setCount(postPage.getCount());
        postPageModel.setNext(postPage.getNext());
        postPageModel.setPrevious(postPage.getPrevious());
        postPageModel.setResults(postModelDataMapper.transform(postPage.getResults()));

        return postPageModel;
    }
}
