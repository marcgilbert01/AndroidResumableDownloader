package com.sumitanantwar.android_resumable_downloader;

/**
 * Created by Sumit Anantwar on 7/13/16.
 */
public interface DownloadCallback
{
    void onDownloadComplete();
    void onDownloadProgress(long completedBytes, long totalBytes);
    void onDownloadFailure(RetryMode retryMode);

}