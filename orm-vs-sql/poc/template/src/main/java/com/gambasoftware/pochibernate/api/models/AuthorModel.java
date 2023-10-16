package com.gambasoftware.pochibernate.api.models;

public class AuthorModel {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static final class AuthorModelBuilder {
        private String id;
        private String name;

        private AuthorModelBuilder() {
        }

        public static AuthorModelBuilder anAuthorModel() {
            return new AuthorModelBuilder();
        }

        public AuthorModelBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public AuthorModelBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public AuthorModel build() {
            AuthorModel authorModel = new AuthorModel();
            authorModel.name = this.name;
            authorModel.id = this.id;
            return authorModel;
        }
    }
}
