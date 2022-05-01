package ss.com.toolkit.view.expandabletextview.model;


import ss.com.toolkit.view.expandabletextview.app.StatusType;

/**
 * @date: on 2018/9/20
 * @author: cretin
 * @email: mxnzp_life@163.com
 * @desc: 为ExpandableTextView添加展开和收回状态的记录
 */
public interface ExpandableStatusFix {
    void setStatus(StatusType status);

    StatusType getStatus();
}
