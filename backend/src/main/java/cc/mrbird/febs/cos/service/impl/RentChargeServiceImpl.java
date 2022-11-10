package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.controller.po.RentChargePo;
import cc.mrbird.febs.cos.entity.CommunityInfo;
import cc.mrbird.febs.cos.entity.HouseInfo;
import cc.mrbird.febs.cos.entity.RentCharge;
import cc.mrbird.febs.cos.dao.RentChargeMapper;
import cc.mrbird.febs.cos.entity.StaffInfo;
import cc.mrbird.febs.cos.entity.vo.RentChargeVo;
import cc.mrbird.febs.cos.service.ICommunityInfoService;
import cc.mrbird.febs.cos.service.IHouseInfoService;
import cc.mrbird.febs.cos.service.IRentChargeService;
import cc.mrbird.febs.cos.service.IStaffInfoService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RentChargeServiceImpl extends ServiceImpl<RentChargeMapper, RentCharge> implements IRentChargeService {

    private final ICommunityInfoService communityInfoService;

    private final IHouseInfoService houseInfoService;

    private final IStaffInfoService staffInfoService;

    /**
     * 查询租房信息包括房屋编号与小区编号
     *
     * @return 结果
     */
    @Override
    public List<RentCharge> selectRentChargeWithHouse() {
        return baseMapper.selectRentChargeWithHouse();
    }

    /**
     * 获取房屋出租负责信息
     *
     * @param rentCharge 参数
     * @return 结果
     */
    @Override
    public List<RentChargeVo> selectRentChargeList(RentChargePo rentCharge) {
        return baseMapper.selectRentChargeList(rentCharge);
    }

    /**
     * 根据时间获取去出租信息
     *
     * @param year  统计年度
     * @param month 统计月度
     * @return 结果
     */
    @Override
    public List<RentChargeVo> selectRentChargeByDate(String year, String month) {
        RentChargePo param = new RentChargePo();
        List<RentChargeVo> rentChargeVoList = baseMapper.selectRentChargeList(param);
        if (CollectionUtil.isEmpty(rentChargeVoList)) {
            return rentChargeVoList;
        }
        boolean monthCheck = StrUtil.isNotEmpty(month);
        rentChargeVoList = rentChargeVoList.stream().filter(e -> year.equals(e.getCheckYear())).collect(Collectors.toList());
        if (monthCheck && StrUtil.isNotEmpty(month)) {
            rentChargeVoList = rentChargeVoList.stream().filter(e -> month.equals(e.getCheckMonth())).collect(Collectors.toList());
        }
        return rentChargeVoList;
    }

    /**
     * 获取各小区租房情况
     *
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectRentChargeByCommunity(String communityCode) {
        // 返回数据
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();
        RentChargePo param = new RentChargePo();
        if (StrUtil.isNotEmpty(communityCode)) {
            param.setCommunityCode(communityCode);
        }
        List<RentChargeVo> rentChargeVoList = baseMapper.selectRentChargeList(param);
        if (CollectionUtil.isEmpty(rentChargeVoList)) {
            return result;
        }
        List<CommunityInfo> communityInfoList = communityInfoService.list(Wrappers.<CommunityInfo>lambdaQuery().eq(CommunityInfo::getDelFlag, 0).eq(StrUtil.isNotEmpty(communityCode), CommunityInfo::getCode, communityCode));
        Map<String, List<RentChargeVo>> rentChargeMap = rentChargeVoList.stream().filter(e -> StrUtil.isNotEmpty(e.getCommunityCode())).collect(Collectors.groupingBy(RentChargeVo::getCommunityCode));
        if (CollectionUtil.isEmpty(communityInfoList) || CollectionUtil.isEmpty(rentChargeMap)) {
            return result;
        }
        for (CommunityInfo communityInfo : communityInfoList) {
            List<RentChargeVo> rentChargeVoItem = rentChargeMap.get(communityInfo.getCode());
            LinkedHashMap<String, Object> rentChargeItem = new LinkedHashMap<String, Object>() {
                {
                    put("community", communityInfo);
                    put("rent", Collections.emptyList());
                }
            };
            if (CollectionUtil.isNotEmpty(rentChargeVoItem)) {
                rentChargeItem.put("rent", rentChargeVoItem);
                continue;
            }
            result.add(rentChargeItem);
        }
        return result;
    }

    /**
     * 房屋出租信息
     *
     * @param houseCode 房屋编号
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectRentChargeByHouse(String houseCode) {
        // 返回数据
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();
        RentChargePo param = new RentChargePo();
        if (StrUtil.isNotEmpty(houseCode)) {
            param.setHouseCode(houseCode);
        }
        List<RentChargeVo> rentChargeVoList = baseMapper.selectRentChargeList(param);
        if (CollectionUtil.isEmpty(rentChargeVoList)) {
            return result;
        }
        List<HouseInfo> houseList = houseInfoService.list(Wrappers.<HouseInfo>lambdaQuery().eq(StrUtil.isNotEmpty(houseCode), HouseInfo::getCode, houseCode).eq(HouseInfo::getDelFlag, 0));
        Map<String, List<RentChargeVo>> rentChargeVoMap = rentChargeVoList.stream().filter(e -> StrUtil.isNotEmpty(e.getHouseCode())).collect(Collectors.groupingBy(RentChargeVo::getHouseCode));
        if (CollectionUtil.isEmpty(houseList) || CollectionUtil.isEmpty(rentChargeVoMap)) {
            return result;
        }
        for (HouseInfo houseInfo : houseList) {
            List<RentChargeVo> rentChargeVoItem = rentChargeVoMap.get(houseInfo.getCode());
            LinkedHashMap<String, Object> rentChargeItem = new LinkedHashMap<String, Object>() {
                {
                    put("house", houseInfo);
                    put("rent", Collections.emptyList());
                }
            };
            if (CollectionUtil.isNotEmpty(rentChargeVoItem)) {
                rentChargeItem.put("rent", rentChargeVoItem);
            }
            result.add(rentChargeItem);
        }
        return result;
    }

    /**
     * 获取员工负责情况
     *
     * @param staffCode 员工编号
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectRentChargeByStaff(String staffCode) {
        // 返回数据
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();
        RentChargePo param = new RentChargePo();
        if (StrUtil.isNotEmpty(staffCode)) {
            param.setStaffCode(staffCode);
        }
        List<RentChargeVo> rentChargeVoList = baseMapper.selectRentChargeList(param);
        if (CollectionUtil.isEmpty(rentChargeVoList)) {
            return result;
        }
        List<StaffInfo> staffInfoList = staffInfoService.list(Wrappers.<StaffInfo>lambdaQuery().eq(StrUtil.isNotEmpty(staffCode), StaffInfo::getStaffCode, staffCode).eq(StaffInfo::getStaffStatus, 1));
        Map<String, List<RentChargeVo>> rentChargeMap = rentChargeVoList.stream().filter(e -> StrUtil.isNotEmpty(e.getStaffCode())).collect(Collectors.groupingBy(RentChargeVo::getStaffCode));
        if (CollectionUtil.isEmpty(rentChargeMap) || CollectionUtil.isEmpty(staffInfoList)) {
            return result;
        }
        for (StaffInfo staff : staffInfoList) {
            List<RentChargeVo> rentChargeVoItem = rentChargeMap.get(staff.getStaffCode());
            LinkedHashMap<String, Object> rentChargeItem = new LinkedHashMap<String, Object>() {
                {
                    put("staff", staff);
                    put("rent", Collections.emptyList());
                }
            };
            if (CollectionUtil.isNotEmpty(rentChargeVoItem)) {
                rentChargeItem.put("rent", rentChargeVoItem);
            }
            result.add(rentChargeItem);
        }
        return result;
    }
}
