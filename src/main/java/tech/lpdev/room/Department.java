package tech.lpdev;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Department {

    NONE(-1),
    EXECUTIVE(1),
    HR(2),
    FINANCE(3),
    PURCHASING(4),
    SALES_AND_MARKETING(5),
    SECURITY(6),
    COMMUNICATIONS(7),
    LEGAL(8),
    IT(9),
    SPECIALTY_ONE(10),
    SPECIALTY_TWO(11);

    private final int id;
}
