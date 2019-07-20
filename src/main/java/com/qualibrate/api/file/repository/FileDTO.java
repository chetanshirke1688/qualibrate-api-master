package com.qualibrate.api.file.repository;

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
public class FileDTO extends File implements Dto {

    private Long id;

    private String uuid;

    private String name;

    private String mime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date timestamp;

    private Long userId;

    private String path;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((mime == null) ? 0 : mime.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
        FileDTO other = (FileDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (mime == null) {
            if (other.mime != null)
                return false;
        } else if (!mime.equals(other.mime))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (path == null) {
            if (other.path != null)
                return false;
        } else if (!path.equals(other.path))
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
        if (uuid == null) {
            if (other.uuid != null)
                return false;
        } else if (!uuid.equals(other.uuid))
            return false;
        return true;
    }
}
