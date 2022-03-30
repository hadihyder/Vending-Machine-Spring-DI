package com.mthree.vendingmachine;

import com.mthree.vendingmachine.controller.VendingMachineController;
import com.mthree.vendingmachine.dao.VendingMachineAuditDao;
import com.mthree.vendingmachine.dao.VendingMachineAuditDaoFileImpl;
import com.mthree.vendingmachine.dao.VendingMachineDao;
import com.mthree.vendingmachine.dao.VendingMachineDaoImpl;
import com.mthree.vendingmachine.service.InsufficientFundsException;
import com.mthree.vendingmachine.service.NoItemInventoryException;
import com.mthree.vendingmachine.service.VendingMachineServiceLayer;
import com.mthree.vendingmachine.service.VendingMachineServiceLayerImpl;
import com.mthree.vendingmachine.ui.UserIO;
import com.mthree.vendingmachine.ui.UserIOConsoleImpl;
import com.mthree.vendingmachine.ui.VendingMachineView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class App {
    public static void main(String[] args)  {

        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.mthree.vendingmachine");
        appContext.refresh();

        VendingMachineController controller = appContext.getBean("vendingMachineController", VendingMachineController.class);
        controller.run();

        //        UserIO io = new UserIOConsoleImpl();
//        VendingMachineView view = new VendingMachineView(io);
//        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoFileImpl();
//        VendingMachineDao dao = new VendingMachineDaoImpl();
//        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(auditDao, dao);
//        VendingMachineController controller = new VendingMachineController(view, service);
//
//        controller.run();
    }
}
