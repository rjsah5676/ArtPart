package com.sc.main.service;

import org.springframework.stereotype.Service;

@Service
public class PolicyServiceImpl implements PolicyService {
    
    @Override
    public String getPolicyContent(String policyName) {
        return policyName; // �� �̻� ������ ���� �ʰ�, �ܼ��� ��å �̸��� ��ȯ
    }
}
