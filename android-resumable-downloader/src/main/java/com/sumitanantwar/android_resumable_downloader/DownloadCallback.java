package com.sumitanantwar.android_resumable_downloader;

import java.util.List;

/**
 * Created by Sumit Anantwar on 7/13/16.
 */
public interface DownloadCallback
{
    void onComplete(Downloadable downloadable);
    void onDownloadComplete();
    void onDownloadProgress(long completedBytes, long totalBytes);
    void onDownloadFailure(RetryMode retryMode);

}
