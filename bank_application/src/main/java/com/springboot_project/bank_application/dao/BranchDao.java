package com.springboot_project.bank_application.dao;

import com.springboot_project.bank_application.model.Branches;

public interface BranchDao {

  Branches findBranch(String address);
}
