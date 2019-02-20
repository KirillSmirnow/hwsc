package hwsc.dto;

import java.util.Date;

public abstract class BaseDto {

    private long id;
    private Date created;
    private Date updated;

    public BaseDto() {
    }

    public BaseDto(long id, Date created, Date updated) {
        this.id = id;
        this.created = created;
        this.updated = updated;
    }

    public final long getId() {
        return id;
    }

    public final Date getCreated() {
        return created;
    }

    public final Date getUpdated() {
        return updated;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseDto baseDto = (BaseDto) o;
        return id == baseDto.id;
    }

    @Override
    public final int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
