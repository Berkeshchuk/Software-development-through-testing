package com.testlab1;

import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class CompanyServiceTest {
    private final ICompanyService service = new CompanyServiceImpl();

    @Test
    void testGetTopLevelParent_FromDeepLevel() {
        Company root = new Company(null, 100);
        Company child = new Company(root, 50);
        Company subChild = new Company(child, 10);

        Company result = service.getTopLevelParent(subChild);

        assertEquals(root, result);
    }

    @Test
    void testGetTopLevelParent_WhenAlreadyRoot() {
        Company root = new Company(null, 100);
        Company result = service.getTopLevelParent(root);

        assertEquals(root, result);
    }

    @Test
    void testEmployeeCount_NoChildren() {
        Company root = new Company(null, 10);
        List<Company> companies = List.of(root);

        long result = service.getEmployeeCountForCompanyAndChildren(root, companies);

        assertEquals(10, result);
    }

    @Test
    void testEmployeeCount_SimpleTree() {
        Company root = new Company(null, 10);
        Company child1 = new Company(root, 5);
        Company child2 = new Company(root, 5);
        List<Company> companies = List.of(root, child1, child2);

        long result = service.getEmployeeCountForCompanyAndChildren(root, companies);

        assertEquals(20, result);
    }

    @Test
    void testTopLevelParent_LoopProtection() {
        Company compA = new Company(null, 10);
        Company compB = new Company(compA, 10);
        compA.setParent(compB);

        assertThrows(RuntimeException.class, () -> service.getTopLevelParent(compA));
    }

    @Test
    void testEmployeeCount_EmptyList() {
        Company root = new Company(null, 100);

        long result = service.getEmployeeCountForCompanyAndChildren(root, Collections.emptyList());

        assertEquals(100, result);
    }

    @Test
    void testEmployeeCount_DisconnectedCompanies() {
        Company main = new Company(null, 10);
        Company stranger = new Company(null, 1000);
        List<Company> companies = List.of(main, stranger);

        long result = service.getEmployeeCountForCompanyAndChildren(main, companies);

        assertEquals(10, result);
    }

    @Test
    void testEmployeeCount_DeepHierarchy() {
        Company root = new Company(null, 1);
        List<Company> all = new ArrayList<>();
        all.add(root);

        Company last = root;
        for (int i = 0; i < 10; i++) {
            Company next = new Company(last, 1);
            all.add(next);
            last = next;
        }

        long result = service.getEmployeeCountForCompanyAndChildren(root, all);

        assertEquals(11, result);
    }

    @Test
    void testEmployeeCount_MultipleSubtrees() {
        Company root = new Company(null, 10);
        Company branch1 = new Company(root, 10);
        Company branch2 = new Company(root, 10);
        Company leaf1 = new Company(branch1, 5);
        Company leaf2 = new Company(branch2, 5);

        List<Company> companies = List.of(root, branch1, branch2, leaf1, leaf2);

        long result = service.getEmployeeCountForCompanyAndChildren(root, companies);

        assertEquals(40, result);
    }

    @Test
    void testGetTopLevelParent_NullInput() {
        assertNull(service.getTopLevelParent(null));
    }
}