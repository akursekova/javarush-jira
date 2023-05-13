package com.javarush.jira.profile.web;

import com.javarush.jira.MatcherFactory;
import com.javarush.jira.profile.ContactTo;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.Contact;
import com.javarush.jira.profile.internal.Profile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.javarush.jira.login.internal.web.UserTestData.*;

public class ProfileTestData {
    public static final MatcherFactory.Matcher<ProfileTo> PROFILE_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(ProfileTo.class);

    public static final MatcherFactory.Matcher<Profile> PROFILE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Profile.class, "id", "lastFailedLogin");



    public static final Set<String> userMailNotifications = new HashSet<>(Arrays.asList("assigned", "deadline", "overdue"));
    public static final Set<String> adminMailNotifications = new HashSet<>(Arrays.asList("three_days_before_deadline", "two_days_before_deadline", "one_day_before_deadline"));



    public static final Long allEmailNotifications = 63L;
    private static final ContactTo userContactToSkype = new ContactTo("skype", "userSkype");
    private static final ContactTo userContactToMob = new ContactTo("mobile", "+01234567890");
    private static final ContactTo userContactToWeb = new ContactTo("website", "user.com");
    private static final ContactTo adminContactToGit = new ContactTo("github", "adminGitHub");
    private static final ContactTo adminContactToTg = new ContactTo("tg", "adminTg");
    private static final ContactTo adminContactToVk = new ContactTo("vk", "adminVk");

    private static final Contact userContactGit = new Contact(USER_ID, "github", "userGit");
    private static final Contact userContactSkype = new Contact(USER_ID, "skype", "userSkype");
    private static final Contact userContactMob = new Contact(USER_ID, "mobile", "+01234567890");
    private static final Contact userContactWeb = new Contact(USER_ID, "website", "user.com");

    private static final Contact invalidUserContact = new Contact(USER_ID, "whatsApp", "whatsApp");
    public static final Set<ContactTo> userContactsTo = new HashSet<>(Arrays.asList(userContactToSkype, userContactToMob, userContactToWeb));
    public static final Set<ContactTo> adminContacts = new HashSet<>(Arrays.asList(adminContactToGit, adminContactToTg, adminContactToVk));
    public static final Set<Contact> userContacts = new HashSet<>(Arrays.asList(userContactGit, userContactSkype, userContactMob, userContactWeb));

    public static final ProfileTo userProfileTo = new ProfileTo(USER_ID, userMailNotifications, userContactsTo);

    public static final ProfileTo adminProfileTo = new ProfileTo(ADMIN_ID, adminMailNotifications, adminContacts);

    public static Profile getProfileWithInvalidContacts() {
        Profile profile = new Profile(USER_ID);
        profile.setContacts(new HashSet<>(Arrays.asList(invalidUserContact)));
        profile.setMailNotifications(allEmailNotifications);
        return profile;
    }

    public static Profile getUpdatedProfile() {
        Profile profile = new Profile(USER_ID);
        profile.setContacts(userContacts);
        profile.setMailNotifications(allEmailNotifications);
        return profile;
    }
}
