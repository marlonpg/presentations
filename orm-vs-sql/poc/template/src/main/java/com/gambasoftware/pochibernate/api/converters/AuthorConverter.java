package com.gambasoftware.pochibernate.api.converters;

import com.gambasoftware.pochibernate.api.models.AuthorModel;
import com.gambasoftware.pochibernate.data.entities.Author;

import java.util.HashSet;
import java.util.Set;

public class AuthorConverter {

    public static AuthorModel convert(Author author) {
        return AuthorModel.AuthorModelBuilder.anAuthorModel()
                .withId(author.getAuthorId())
                .withName(author.getName())
                .build();
    }

    public static Set<AuthorModel> convert(Set<Author> authors) {
        Set<AuthorModel> authorModels = new HashSet<>();
        for (Author author : authors) {
            authorModels.add(AuthorModel.AuthorModelBuilder.anAuthorModel()
                    .withId(author.getAuthorId())
                    .withName(author.getName())
                    .build());
        }
        return authorModels;
    }
}
