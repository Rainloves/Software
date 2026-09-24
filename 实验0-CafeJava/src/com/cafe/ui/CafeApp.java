package com.cafe.ui;

import java.util.InputMismatchException;
import java.util.Scanner;


public class CafeApp {

    /**
     * 个性化要求①：欢迎信息必须包含本人学号后四位。
     * 学号 2026325011351 → 后四位 1351。
     */
    private static final String USER_NO = "1351";

    /** 系统名称集中定义，以后改名只需改这一处。 */
    private static final String SYSTEM_NAME = "CafeJava 咖啡店管理系统";

    /**
     * 输入扫描器。
     * 设计理由：声明成成员变量而不是在每个方法里各建一个 Scanner，
     * 因为多个 Scanner 同时包装 System.in 会在缓冲区里互相"抢"数据；
     * 同时生命周期也更清晰，程序结束时统一 close()。
     */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * 程序入口。
     * 设计理由：先 new 出对象再调用实例方法，是为了让 Scanner 这类资源有明确的归属者；
     * 后续把界面层改成持有 OrderService 等业务对象时，只需改这一个地方。
     */
    public static void main(String[] args) {
        new CafeApp().start();
    }

    /**
     * 主流程：打印欢迎信息 → 循环显示菜单并处理选择 → 用户选择 0 后退出。
     */
    public void start() {
        printWelcome();

        // 用 while(true) 是因为菜单需要反复显示，直到用户选择 0 退出
        while (true) {
            printMenu();
            int choice = readChoice();
            if (choice == 0) {
                break;
            }
            handleChoice(choice);
        }

        System.out.println();
        System.out.println("感谢使用 " + SYSTEM_NAME + "，再见！");
        scanner.close();
    }

    /** 打印系统启动欢迎信息（含个性化学号后四位）。 */
    private void printWelcome() {
        System.out.println("==================================================");
        System.out.println("        欢迎使用 " + SYSTEM_NAME);
        System.out.println("        用户编号：" + USER_NO);
        System.out.println("        版本：v0.1（任务0 · 项目骨架）");
        System.out.println("==================================================");
        System.out.println();
    }

    /** 打印控制台功能菜单。 */
    private void printMenu() {
        System.out.println("------------------- 功能菜单 -------------------");
        System.out.println("  1. 查看咖啡菜单");
        System.out.println("  2. 点单（加入购物车）");
        // 个人化改写：将"查看购物车"改为"查看我的美式咖啡"
        System.out.println("  3. 查看我的美式咖啡");
        System.out.println("  4. 结账（生成订单）");
        System.out.println("  5. 制作咖啡（模拟冲泡流程）");
        System.out.println("  6. 库存管理（查看/补货）");
        System.out.println("  7. 会员注册与折扣");
        System.out.println("  8. 销售统计报表");
        System.out.println("  9. 多线程订单处理");
        System.out.println("  0. 退出系统");
        System.out.println("------------------------------------------------");
    }

    /**
     * 读取用户输入的功能编号。
     *
     * 设计理由：编号本身是整数，用 nextInt() 可以让 Scanner 直接完成校验，
     * 输入非数字时抛出 InputMismatchException，被捕获后给出友好提示，
     * 而不是让程序崩溃（这是控制台程序最基本的健壮性要求）。
     *
     * @return 用户输入的编号；输入非法时返回 -1
     */
    private int readChoice() {
        System.out.print("请输入功能编号（0-9）：");
        try {
            int choice = scanner.nextInt();
            // 关键设计理由：nextInt() 只读走数字本身，把行尾的换行符留在了缓冲区里。
            // 这里必须再调用一次 nextLine() 把它"吃掉"，
            // 否则后面任何一次 nextLine() 都会先读到一个空行，导致输入被凭空跳过
            // （例如后续做"会员注册"要输入姓名时，姓名会直接读成空字符串）。
            scanner.nextLine();
            return choice;
        } catch (InputMismatchException e) {
            // 输入的不是整数：必须把这个非法记号连同换行一起读掉，
            // 否则它会一直留在缓冲区里，下一次 nextInt() 立刻又抛异常，形成死循环。
            scanner.nextLine();
            System.out.println(">> 输入无效，请输入 0-9 之间的数字。");
            System.out.println();
            return -1;
        }
    }

    /**
     * 根据用户选择给出对应提示（本任务只做界面占位，不含业务逻辑）。
     *
     * @param choice 用户输入的功能编号
     */
    private void handleChoice(int choice) {
        System.out.println();
        // switch 比一串 if-else 更贴合"按编号分派"的语义，可读性更好
        switch (choice) {
            case 1:
                System.out.println(">> 查看咖啡菜单：菜单数据将在任务13从 data/menu.txt 读取，当前暂无数据。");
                break;
            case 2:
                System.out.println(">> 点单：已进入点单流程，购物车逻辑将在后续任务实现。");
                break;
            case 3:
                System.out.println(">> 查看我的咖啡篮：购物车展示逻辑将在后续任务实现。");
                break;
            case 4:
                System.out.println(">> 结账：生成订单逻辑将在后续任务实现。");
                break;
            case 5:
                System.out.println(">> 制作咖啡：模拟冲泡流程将在后续任务实现。");
                break;
            case 6:
                System.out.println(">> 库存管理：库存查看与补货逻辑将在后续任务实现。");
                break;
            case 7:
                System.out.println(">> 会员注册与折扣：会员与折扣逻辑将在后续任务实现。");
                break;
            case 8:
                System.out.println(">> 销售统计报表：统计报表逻辑将在后续任务实现。");
                break;
            case 9:
                System.out.println(">> 多线程订单处理：并发处理逻辑将在后续任务实现。");
                break;
            default:
                // 兜底分支：防止用户输入 10、-3 之类的越界编号时程序毫无反应
                System.out.println(">> 无效的功能编号，请输入 0-9 之间的数字。");
                break;
        }
        System.out.println();
    }
}
