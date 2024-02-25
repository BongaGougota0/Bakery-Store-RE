package za.co.bakery.app.security;

import za.co.bakery.backend.data.entity.User;

@FunctionalInterface
public interface CurrentUser {
    User getUser();
}
