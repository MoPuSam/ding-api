package com.oulam.dingding.bean;

/**
 * 部门实体
 * @param ;id,钉钉的部门id,部门名称,父部门parentid
 */
public class Department {
    private Long id;

    private Long dId;

    private String name;

    private Long parentid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getdId() {
        return dId;
    }

    public void setdId(Long dId) {
        this.dId = dId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Department){
            Department d=(Department) obj;
            return (dId.equals(d.dId));
        }else{
            return super.equals(obj);
        }
    }

    @Override
    public int hashCode() {
        return dId != null ? dId.hashCode() : 0;
    }
}