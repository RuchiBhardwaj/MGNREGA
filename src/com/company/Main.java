package com.company;

import com.company.service.impl.bdoImpl;
import com.company.service.impl.gpmImpl;
import com.company.service.impl.memberImpl;
import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException, ParseException {
	// write your code here
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            bdoImpl bdo = new bdoImpl();
            gpmImpl gpm = new gpmImpl();
            memberImpl member = new memberImpl();
            Scanner sc = new Scanner(System.in);
            System.out.print("Choose your option to Login as Different User Pofile\n 1. BDO \n 2. GPM \n 3. MEMBER\n 4. Exit \n");
            int i = sc.nextInt();
            while (i <= 4) {
                if (i == 1)
                    bdo.bdoLogin(bufferedReader);
                else if (i == 2)
                    gpm.gpmLogin();
                else if (i == 3)
                    member.memberLogin(bufferedReader);
                else if (i ==4){
                    System.out.println("Thank you!!!");
                    break;
                }
                System.out.print("Choose your option to Login as Different User Pofile\n 1. BDO \n 2. GPM \n 3. MEMBER\n 4. Exit \n");
                i = sc.nextInt();
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
