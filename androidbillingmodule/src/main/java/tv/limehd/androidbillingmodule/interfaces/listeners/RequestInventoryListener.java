package tv.limehd.androidbillingmodule.interfaces.listeners;

import androidx.annotation.NonNull;

import java.util.List;

public interface RequestInventoryListener {
    void onResult(@NonNull List<String> inventory);
}
