package sprint.sprint1_3.domain;

import lombok.Getter;

@Getter
public enum MemberShip {
    BRONZE(0L), SILVER(30000L), GOLD(100000L);

    private Long minimum;

    MemberShip(Long minimum) {
        this.minimum = minimum;
    }
}
