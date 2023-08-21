package com.obss.hrms.service;

import com.obss.hrms.converter.BlackListConverter;
import com.obss.hrms.entity.*;
import com.obss.hrms.exception.BlackListNotFoundException;
import com.obss.hrms.exception.Constant;
import com.obss.hrms.exception.JobSeekerDontOccupyBlackListException;
import com.obss.hrms.exception.JobSeekerIsAlreadyOccupyingBlackListException;
import com.obss.hrms.repository.BlackListRepository;
import com.obss.hrms.request.CreateBlackListRequest;
import com.obss.hrms.response.CreateBlackListResponse;
import com.obss.hrms.response.GetBlackListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.ldap.LdapName;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlackListService {


    private final BlackListRepository blackListRepository;
    private final JobSeekerService jobSeekerService;
    private final HumanResourceService humanResourceService;
    private final ApplyAdvertisementService applyAdvertisementService;
    private final BlackListConverter blackListConverter;

    public CreateBlackListResponse create(CreateBlackListRequest request) throws InvalidNameException {
        Name humanResourceName = new LdapName(request.humanResourceId());
        HumanResource humanResource = humanResourceService.findNameByDn(humanResourceName);
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                humanResource.getDn().toString(),
                humanResource.getFullName(),
                humanResource.getLastName(),
                humanResource.getDisplayName()
        );
        JobSeeker jobSeeker = jobSeekerService.findById(request.jobSeekerId());
        return (CreateBlackListResponse) blackListRepository.findByJobSeeker_Id(request.jobSeekerId())
                .map(existingBlackList -> {
                    throw new JobSeekerIsAlreadyOccupyingBlackListException(
                            Constant.JOB_SEEKER_HAVE_ALREADY_BEEN_OCCUPYING_BLACK_LIST);
                })
                .orElseGet(() -> {

//                    karalısteye alınınca jobseeker ıcınde bulunan InblackLıst degıskenını true ya cekıyoruz
                    jobSeekerService.updateInBlackList(jobSeeker, true);
//                    eski applyAdvertisementlerin statusunu reject yapıyoruz
                    oldApplicationReject(request.jobSeekerId());
                    BlackList blackList = new BlackList(
                            request.description(),
                            jobSeeker,
                            humanResourceEntity

                    );
                    BlackList save = blackListRepository.save(blackList);
                    return blackListConverter.convert(save);
                });
    }

    public List<GetBlackListResponse> findAll(){
        return blackListConverter.convertGet(blackListRepository.findAll());
    }

    public void delete(String blackListId){
        BlackList byId = findById(blackListId);
        jobSeekerService.updateInBlackList(byId.getJobSeeker(),false);
        blackListRepository.delete(byId);

    }
    public GetBlackListResponse findByBlackListId(String blackListId){
        return blackListConverter.convertGet(findById(blackListId));
    }
    public GetBlackListResponse findBlackListByJobSeekerId(String jobSeekerId){
        return blackListConverter.convertGet(findByJobSeekerId(jobSeekerId));
    }

    protected BlackList findById(String blackListId){
        return blackListRepository
                .findById(blackListId)
                .orElseThrow(()-> new BlackListNotFoundException(Constant.BLACK_LIST_NOT_FOUND));
    }
    protected BlackList findByJobSeekerId(String jobSeekerId){
        return blackListRepository
                .findByJobSeeker_Id(jobSeekerId)
                .orElseThrow(()-> new JobSeekerDontOccupyBlackListException(
                        Constant.JOB_SEEKER_DONT_OCCUPY_BLACK_LIST));
    }
    private void oldApplicationReject(String jobSeekerId) {
        List<ApplyAdvertisement> byJobSeeker_id = applyAdvertisementService.findByJobSeeker_Id(jobSeekerId);

                byJobSeeker_id.forEach(applyAdvertisement ->
                        applyAdvertisementService.updateApplyAdvertisementStatue("reject",applyAdvertisement.getId()));


    }
}
