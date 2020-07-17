package com.company;

import com.company.service.impl.bdoImpl;
import com.company.service.impl.gpmImpl;
import com.company.service.impl.memberImpl;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException, ParseException {
	// write your code here
        bdoImpl bdo = new bdoImpl();
        gpmImpl gpm = new gpmImpl();
        memberImpl member = new memberImpl();
        Scanner sc = new Scanner(System.in);
        System.out.print("Choose your option to Login as Different User Pofile\n 1. BDO \n 2. GPM \n 3. MEMBER\n");
        int i=sc.nextInt();
        while(i<=3){
            if ( i==1){
                bdo.bdoLogin();
            }
            if(i==2){
                gpm.gpmLogin();
            }
            if (i==3){
                member.memberLogin();
            }
        }
    }
}
