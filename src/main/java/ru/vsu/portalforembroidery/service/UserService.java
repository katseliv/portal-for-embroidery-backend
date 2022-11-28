package ru.vsu.portalforembroidery.service;

import org.springframework.data.domain.Pageable;
import ru.vsu.portalforembroidery.exception.EntityAlreadyExistsException;
import ru.vsu.portalforembroidery.model.Provider;
import ru.vsu.portalforembroidery.model.dto.UserDetailsDto;
import ru.vsu.portalforembroidery.model.dto.UserDto;
import ru.vsu.portalforembroidery.model.dto.UserRegistrationDto;
import ru.vsu.portalforembroidery.model.dto.view.UserForListDto;
import ru.vsu.portalforembroidery.model.dto.view.UserViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;

import java.util.List;

public interface UserService {

    int createUser(UserRegistrationDto userRegistrationDto, Provider provider) throws EntityAlreadyExistsException;

    UserDto getUserById(int id);

    UserViewDto getUserViewById(int id);

    UserDetailsDto getUserDetailsById(int id);

    int getUserIdByEmail(String email);

    UserDetailsDto getUserByEmail(String email);

    boolean existsByUserEmail(String email);

    void updateUserById(UserDto userDto, int id);

    void deleteUserById(int id);

    ViewListPage<UserForListDto> getViewListPage(String page, String size);

    List<UserForListDto> listDesigners(Pageable pageable);

    List<UserForListDto> listUsers(Pageable pageable);

    int numberOfUsers();

}
