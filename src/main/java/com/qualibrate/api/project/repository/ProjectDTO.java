package com.qualibrate.api.project.repository;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qualibrate.api.commons.transformer.Dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:chetan.shirke1688@gmail.com">Chetan Shirke</a>
 *
 */
@Setter
@Getter
public class ProjectDTO extends Project implements Dto {

    private String icon;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date timestamp;

    private Long userId;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((icon == null) ? 0 : icon.hashCode());
        result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProjectDTO other = (ProjectDTO) obj;
        if (icon == null) {
            if (other.icon != null)
                return false;
        } else if (!icon.equals(other.icon))
            return false;
        if (timestamp == null) {
            if (other.timestamp != null)
                return false;
        } else if (!timestamp.equals(other.timestamp))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }
}
