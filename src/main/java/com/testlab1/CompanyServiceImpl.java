package com.testlab1;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompanyServiceImpl implements ICompanyService {

    @Override
    public Company getTopLevelParent(Company child) {
        if (child == null) {
            return null;
        }

        Company current = child;
        Set<Company> visited = new HashSet<>();

        while (current.getParent() != null) {
            if (visited.contains(current)) {
                throw new RuntimeException("Circular reference detected!");
            }
            visited.add(current);
            current = current.getParent();
        }

        return current;
    }

    @Override
    public long getEmployeeCountForCompanyAndChildren(Company company, List<Company> companies) {
        if (company == null) return 0;

        long totalCount = company.getEmployeeCount();

        if (companies == null || companies.isEmpty()) {
            return totalCount;
        }

        for (Company potentialChild : companies) {
            if (potentialChild.getParent() == company) {
                totalCount += getEmployeeCountForCompanyAndChildren(potentialChild, companies);
            }
        }

        return totalCount;
    }
}