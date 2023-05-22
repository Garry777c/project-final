package com.javarush.jira.profile.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.profile.ContactTo;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.Profile;
import com.javarush.jira.profile.internal.ProfileMapper;
import com.javarush.jira.profile.internal.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.profile.web.ProfileTestData.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.javarush.jira.profile.web.ProfileRestController.REST_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    ProfileMapper profileMapper;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_MATCHER.contentJson(profileTo));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    public void testUpdate() throws Exception {
        ProfileTo profileUpdated = profileTo; // Create a sample of ProfileTo object
        Set<String> mailNotifications = new HashSet<>(List.of("assigned", "deadline", "overdue"));
        Set<ContactTo> contactTos = new HashSet<>(List.of(new ContactTo("skype", "userSkype"),
                new ContactTo("mobile", "+01234567890"),
                new ContactTo("website", "user.com")));


        profileUpdated.setMailNotifications(mailNotifications);
        profileUpdated.setContacts(contactTos);

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithMailNotifications(profileUpdated, mailNotifications)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Profile profileAfter = profileRepository.getExisted(profileTo.id());
        ProfileTo profileToAfter = profileMapper.toTo(profileAfter);
        PROFILE_TO_MATCHER.assertMatch(profileToAfter, profileTo);
    }
}