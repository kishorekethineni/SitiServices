package com.example.android.sitiservices.Services;

public class D_ProfileOfWorker {


    public String WorkerName;
    public String WorkerMobileNumber;
    public String WorkerEmail;
    public String WorkerAddress;
    public String WorkerImage;
    public String WorkerBio;

    public D_ProfileOfWorker(String workerName, String workerMobileNumber, String workerEmail, String workerAddress, String workerImage, String workerBio) {
        WorkerName = workerName;
        WorkerMobileNumber = workerMobileNumber;
        WorkerEmail = workerEmail;
        WorkerAddress = workerAddress;
        WorkerImage = workerImage;
        WorkerBio = workerBio;
    }

    public D_ProfileOfWorker() {
    }
}
