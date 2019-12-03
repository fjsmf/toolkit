package ss.com.toolkit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.sunhapper.x.spedit.mention.span.IntegratedSpan;
import com.sunhapper.x.spedit.view.SpXEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import spedit.mention.MentionUser;
import ss.com.toolkit.base.BaseActivity;

import static com.sunhapper.x.spedit.SpUtilKt.insertSpannableString;

public class EditTextActivity extends BaseActivity {
    private static final String TAG = "EditTextActivity";
    @BindView(R.id.spEdt)
    SpXEditText spEdt;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    MemAdapter adapter;
    MyTextWatcher textWatcher;

    List<String> memberInfosSrc = new ArrayList<>();
    List<String> memberInfos = new ArrayList<>();
    Map<MentionUser, Long> atUsers = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.layout_edittext);
        super.onCreate(savedInstanceState);
        initEditView();
        initList();
    }

    private void initList() {
        rv_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_list.setAdapter(adapter = new MemAdapter());
        AndroidSchedulers.mainThread().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (i++ < 30) {
                    memberInfosSrc.add("July" + i);
                }
                memberInfos.addAll(memberInfosSrc);
                adapter.notifyDataSetChanged();
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

    class MemHV extends RecyclerView.ViewHolder {
        TextView tv_name;

        public MemHV(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }

    private void initEditView() {
        spEdt.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        spEdt.addTextChangedListener(textWatcher = new MyTextWatcher());
    }

    private void hideMemChooseView() {
        rv_list.setVisibility(View.GONE);
    }

    private void processMemData(CharSequence atSc) {
        if (atSc == null) {
            memberInfos.clear();
            memberInfos.addAll(memberInfosSrc);
        } else {
            List<String> list = new ArrayList<>();
            for (String s : memberInfosSrc) {
                if (s.toLowerCase().contains(atSc.toString().toLowerCase())) {
                    list.add(s);
                }
            }
            memberInfos.clear();
            memberInfos.addAll(list);
        }

        adapter.notifyDataSetChanged();
        showMemChooseView();
    }

    private void showMemChooseView() {
        rv_list.setVisibility(View.VISIBLE);
    }


//    public void insertMention(View view) {
//        replace(new MentionUser("abc").getSpannableString(), textWatcher.getAtSize());
//    }

    private void replace(String charSequence, int atSize) {
        MentionUser user = new MentionUser(charSequence);
        atUsers.put(user, 1L);
        LogUtils.tag(this.getClass().getName()).d("replace atUsers length:"+atUsers.size());
        insertSpannableString(spEdt.getText(), user.getSpannableString(), atSize);
    }

    class MemAdapter extends RecyclerView.Adapter<MemHV> {

        @NonNull
        @Override
        public MemHV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MemHV(LayoutInflater.from(parent.getContext()).inflate(R.layout.mem_choose_item, null));
        }

        @Override
        public void onBindViewHolder(@NonNull MemHV holder, int position) {
            holder.tv_name.setText(memberInfos.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replace(memberInfos.get(position), textWatcher.getAtSize());
                    textWatcher.resetAt();
                }
            });
        }

        @Override
        public int getItemCount() {
            return memberInfos == null ? 0 : memberInfos.size();
        }
    }

    class MyTextWatcher implements TextWatcher {
        boolean hasChar;
        CharSequence atSc;
        String beforeStr;
        Editable beforeEditable;
        int atPos = -1;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            beforeStr = s == null ? null : s.toString();
            beforeEditable = new Editable.Factory().newEditable(spEdt.getText());
            if (s != null && s.length() > 0) {
                hasChar = true;
            } else {
                hasChar = false;
            }
            LogUtils.tag(TAG).d(String.format("beforeTextChanged s: %s, start : %d, after : %d, count : %d", s, start, after, count));
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (count == 1 && "@".equals(s.subSequence(start, start + 1).toString())) {
                atPos = start;
                atSc = null;
                processMemData(atSc);
            } else if (atPos > -1){
                if (before > 0) {
                    if (start <= atPos && start + before > atPos) {
                        atSc = null;
                        atPos = -1;
                        hideMemChooseView();
                    } else if (start < atPos) {
                        atPos -= before;
                        hideMemChooseView();
                    } else {
                        int selectionStart = spEdt.getSelectionStart();
                        atSc = s.subSequence(atPos + 1, selectionStart);
                        processMemData(atSc);
                            /*int selectionStart = spEdt.getSelectionStart();
                            if (selectionStart > atPos) {
                                atSc = s.subSequence(atPos + 1, selectionStart);
                                needshowMemChooseView = true;
                                processMemData(atSc);
                            } else {
                                needshowMemChooseView = false;
                                hideMemChooseView();
                            }*/
                    }
                } else {
                    if (start <= atPos) {
                        atPos += count;
                        hideMemChooseView();
                    } else {
                        int selectionStart = spEdt.getSelectionStart();
                        if (selectionStart > atPos) {
                            atSc = s.subSequence(atPos + 1, selectionStart);
                            processMemData(atSc);
                        } else {
                            hideMemChooseView();
                        }
                    }
                }
            }

            if (before > 0) {
                MentionUser[] mentionUsers = beforeEditable.getSpans(start, start + before, MentionUser.class);
                LogUtils.tag(this.getClass().getName()).d( "delete mentionUsers:"+ mentionUsers.length);
                if (mentionUsers != null && mentionUsers.length > 0) {
                    for (MentionUser span : mentionUsers) {
                        atUsers.remove(span);
                        LogUtils.tag(this.getClass().getName()).d("delete mention user:" + span  + ", atUsers size : "+atUsers.size());
                    }
                }
            }
            LogUtils.tag(TAG).d(String.format("onTextChanged s: %s, start : %d, before : %d, count : %d, beforeCs:%s", s, start, before, count, beforeStr));
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!hasChar || s == null || s.length() < 1) {
//                    resetBtnSend();
            }
        }

        public int getAtSize() {
            return atSc == null ? 1 : atSc.length() + 1;
        }

        public void resetAt() {
            atSc = null;
            atPos = -1;
            hideMemChooseView();
        }
    }
}
