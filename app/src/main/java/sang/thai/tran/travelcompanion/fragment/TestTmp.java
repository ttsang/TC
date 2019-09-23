package sang.thai.tran.travelcompanion.fragment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import sang.thai.tran.travelcompanion.interfaces.ResultMultiChoiceDialog;

public class TestTmp extends BaseFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setOnClickAndShowDialog(null, null, new ResultMultiChoiceDialog() {
            @Override
            public void getListSelectedItem(@NotNull List<String> list) {

            }
        });
    }
}
