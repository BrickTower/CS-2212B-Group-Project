package com.mapstob.group35.core;

import java.util.List;

/**
 * This Class is used simply to store and retrieve whatever User,
 * whether it's a {@link PlayerProfile} or {@link ParentProfile},
 * and allow for any of the screens (Main Menu, Tutorial, Game, Stats) to
 * easily retrieve the user at any time.
 *
 * A user will be deposited into this role after successfully logging in via
 * the controller at {@link com.mapstob.group35.LoginController}.
 *
 * @version 1
 * @author Ethan Gregory
 */
public class loggedUser {
    ///  The currently logged-in user. Either a {@link PlayerProfile} or a {@link ParentProfile}.
    private static PlayerProfile currUser;

    /**
     * Sets a {@link PlayerProfile} as the Current User program-wide. Should only be used in the
     * {@link com.mapstob.group35.LoginController} class, due to it only being necessary when logging in.
     * @param user A Valid {@link PlayerProfile} or {@link ParentProfile} class. The code will accomodate for both.
     * @author Ethan Gregory
     */
    public void setCurrUser(PlayerProfile user) {
        if (user instanceof ParentProfile) { // IF MEMBER OF PARENTPROFILE (SUBCLASS) DECLARE IT AS SUCH
            currUser = (ParentProfile) user;
        } else {
            currUser = user;
        }
    }

    /**
     * Returns the currently logged-in Profile
     * @return either a {@link PlayerProfile} or {@link ParentProfile} Object, depending on class created.
     * @author Ethan Gregory
     */
    public PlayerProfile getCurrUser() {
        if (currUser instanceof ParentProfile) { // IF MEMBER OF PARENTPROFILE (SUBCLASS) DECLARE IT AS SUCH
            return (ParentProfile) currUser;
        } else {
            return currUser;
        }
    }
}
