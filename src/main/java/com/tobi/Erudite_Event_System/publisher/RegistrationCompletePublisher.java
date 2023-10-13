package com.tobi.Erudite_Event_System.publisher;

import com.tobi.Erudite_Event_System.users.entity.Users;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompletePublisher extends ApplicationEvent {

    private Users user;
    private String applicationUrl;

    public RegistrationCompletePublisher(Users user, String applicationUrl){
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
