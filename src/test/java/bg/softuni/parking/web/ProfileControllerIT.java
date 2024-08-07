//package bg.softuni.parking.web;
//
//import bg.softuni.parking.model.dto.UserProfileDto;
//import bg.softuni.parking.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static bg.softuni.parking.Constants.USERNAME;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//class ProfileControllerIT {
//
//  @Autowired
//  private MockMvc mockMvc;
//
//  @MockBean
//  private UserService userService;
//
//  @Test
//  @WithMockUser(username = USERNAME)
//  void testViewProfile() throws Exception {
//    UserProfileDto userProfileDto = new UserProfileDto();
//    when(userService.getUserProfile(USERNAME)).thenReturn(userProfileDto);
//
//    mockMvc.perform(get("/profile"))
//        .andExpect(status().isOk())
//        .andExpect(view().name("profile"))
//        .andExpect(model().attributeExists("user"))
//        .andExpect(model().attribute("user", userProfileDto));
//
//    verify(userService, times(1)).getUserProfile(USERNAME);
//  }
//
//  @Test
//  @WithMockUser(username = USERNAME)
//  void testChangePasswordForm() throws Exception {
//    mockMvc.perform(get("/change-password"))
//        .andExpect(status().isOk())
//        .andExpect(view().name("change-password"))
//        .andExpect(model().attributeExists("changePasswordDto"));
//  }
//
//}


package bg.softuni.parking.web;

import bg.softuni.parking.model.dto.UserProfileDto;
import bg.softuni.parking.model.dto.UserUpdateDto;
import bg.softuni.parking.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static bg.softuni.parking.Constants.USERNAME;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProfileControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Test
  @WithMockUser(username = USERNAME)
  void testViewProfile() throws Exception {
    UserProfileDto userProfileDto = new UserProfileDto();
    when(userService.getUserProfile(USERNAME)).thenReturn(userProfileDto);

    mockMvc.perform(get("/profile"))
            .andExpect(status().isOk())
            .andExpect(view().name("profile"))
            .andExpect(model().attributeExists("user"))
            .andExpect(model().attribute("user", userProfileDto));

    verify(userService, times(1)).getUserProfile(USERNAME);
  }

  @Test
  @WithMockUser(username = USERNAME)
  void testChangePasswordForm() throws Exception {
    mockMvc.perform(get("/change-password"))
            .andExpect(status().isOk())
            .andExpect(view().name("change-password"))
            .andExpect(model().attributeExists("changePasswordDto"));
  }

//  @Test
//  @WithMockUser(username = USERNAME)
//  void testEditProfile() throws Exception {
//    UserProfileDto userProfileDto = new UserProfileDto();
//    when(userService.getUserProfile(USERNAME)).thenReturn(userProfileDto);
//
//    mockMvc.perform(get("/profile/edit"))
//            .andExpect(status().isOk())
//            .andExpect(view().name("profile-edit"))
//            .andExpect(model().attributeExists("user"))
//            .andExpect(model().attribute("user", userProfileDto));
//
//    verify(userService, times(1)).getUserProfile(USERNAME);
//  }
//
//  @Test
//  @WithMockUser(username = USERNAME)
//  void testUpdateProfile() throws Exception {
//    UserProfileDto userProfileDto = new UserProfileDto();
//    when(userService.getUserProfile(USERNAME)).thenReturn(userProfileDto);
//
//    mockMvc.perform(post("/profile/update")
//                    .flashAttr("userUpdateDto", new UserUpdateDto().setUsername(USERNAME).setEmail("test@example.com")))
//            .andExpect(status().is3xxRedirection())
//            .andExpect(redirectedUrl("/profile"));
//
//    verify(userService).updateUser(any(UserUpdateDto.class));
//  }
}
