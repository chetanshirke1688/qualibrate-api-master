package com.qualibrate.api.user.repository;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.qualibrate.api.file.repository.FileRecord;
import com.qualibrate.api.project.repository.ProjectRecord;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="mailto:chetan.shirke1688@gmail.com">Chetan Shirke</a>
 *
 * User Entity represents Database Table "user"
 */

@Entity(name = "user")
@Data
@NoArgsConstructor
public class UserRecord implements com.qualibrate.api.commons.transformer.Entity {

    @Id
    @NotNull
    @Column(name = "id")
    @GeneratedValue
    private long id;

    @Size(max = 20)
    private String uid;

    @Size(max = 20)
    private String provider;

    @Size(max = 20)
    private String firstName;

    @Size(max = 20)
    private String lastName;

    @Size(max = 50)
    private String email;

    private boolean active;

    private Date activatedAt;

    private Date loginAt;

    private Date logoutAt;

    private Date timestamp;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    Set<ProjectRecord> projects;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    Set<FileRecord> files;

    public UserRecord(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email =  user.getEmail();
        this.timestamp = new Date();
    }


    public UserRecord(UserDTO userDto) {
        this.firstName = userDto.getFirstName();
        this.lastName = userDto.getLastName();
        this.email =  userDto.getEmail();
        this.id = userDto.getId();
        this.timestamp = new Date();
    }
}
