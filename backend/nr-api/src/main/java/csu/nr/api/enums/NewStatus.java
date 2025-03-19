package csu.nr.api.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import csu.nr.common.exception.BadRequestException;
import lombok.Getter;

@Getter
public enum NewStatus {
    FROZEN(0, "禁止使用"),
    NORMAL(1, "已激活"),
    ;
    @EnumValue
    int value;
    String desc;

    NewStatus(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static NewStatus of(int value) {
        if (value == 0) {
            return FROZEN;
        }
        if (value == 1) {
            return NORMAL;
        }
        throw new BadRequestException("账户状态错误");
    }
}