package com.javarush.jira.profile.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.Profile;
import com.javarush.jira.profile.internal.ProfileMapperImpl;
import com.javarush.jira.profile.internal.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.HashSet;

import static com.javarush.jira.common.util.JsonUtil.writeValue;
import static com.javarush.jira.login.internal.web.UserTestData.USER_MAIL;
import static com.javarush.jira.profile.web.ProfileRestController.REST_URL;
import static com.javarush.jira.login.internal.web.UserTestData.*;
import static com.javarush.jira.profile.web.ProfileTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    ProfileMapperImpl mapper;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getUserProfile() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_TO_MATCHER.contentJson(userProfileTo));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAdminProfile() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_TO_MATCHER.contentJson(adminProfileTo));
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update_addEmailNotificationsAndContacts_shouldReturnNoContent() throws Exception {
        ProfileTo updatedTo = mapper.toTo(getUpdatedProfile());
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Profile dbProfileAfter = profileRepository.getOrCreate(USER_ID);
        Profile updated = getUpdatedProfile();
        PROFILE_MATCHER.assertMatch(dbProfileAfter, updated);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update_addInvalidContact_shouldReturnIsUnprocessableEntity() throws Exception {
        ProfileTo invalid = mapper.toTo(getProfileWithInvalidContacts());
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update_addInvalidEmailNotification_shouldReturnIsUnprocessableEntity() throws Exception {
        ProfileTo invalid = mapper.toTo(getUpdatedProfile());
        invalid.setMailNotifications(new HashSet<>(Arrays.asList("month_before_deadline")));
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
