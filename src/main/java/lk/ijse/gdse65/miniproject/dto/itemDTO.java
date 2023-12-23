package lk.ijse.gdse65.miniproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class itemDTO implements Serializable {
//    private String code;
    private String desc;
    private int qty;
    private double unitPrice;
}

