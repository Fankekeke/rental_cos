package cc.mrbird.febs.cos.entity.vo;

import cc.mrbird.febs.cos.entity.RentCharge;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 租房负责返回信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RentChargeVo extends RentCharge implements Serializable {

    private String houseAddress;

    private String indoorMap;

    private Integer decorationType;

    private Integer houseType;

    private String tags;

    private String communityName;

    private String businessDistrict;

    private String province;

    private String city;

    private String area;

    private String rentPrice;

    private String checkYear;

    private String checkMonth;

}
