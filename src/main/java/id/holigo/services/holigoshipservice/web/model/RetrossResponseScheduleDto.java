package id.holigo.services.holigoshipservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RetrossResponseScheduleDto implements Serializable {
    private String error_code;
    private String error_msg;
    private String org;
    private String des;
    private String trip;
    private String tgl_dep;
    private RetrossScheduleDto schedule;

}
