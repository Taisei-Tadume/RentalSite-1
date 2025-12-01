package jp.ken.jdbc.common.validator.groups;

import jakarta.validation.GroupSequence;

@GroupSequence(value = {ValidGroup1.class, ValidGroup2.class})
public interface ValidGroupOrder {

}
