package com.mapstob.group35.core;

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

    public void setCurrUser(PlayerProfile user) {
        if (user instanceof ParentProfile) { // IF MEMBER OF PARENTPROFILE (SUBCLASS) DECLARE IT AS SUCH
            currUser = (ParentProfile) user;
        } else {
            currUser = user;
        }
    }

    public PlayerProfile getCurrUser() {
        if (currUser instanceof ParentProfile) { // IF MEMBER OF PARENTPROFILE (SUBCLASS) DECLARE IT AS SUCH
            return (ParentProfile) currUser;
        } else {
            return currUser;
        }
    }
}
