package com.poly.demo.admincontroller;

import com.poly.demo.Dao.OrderDao;
import com.poly.demo.Dao.UsersDao;
import com.poly.demo.reqRespModels.*;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminDashboardController {
    private final OrderDao orderDao;
    private final UsersDao usersDao;
    @GetMapping("/dailyRevenue")
    public ResponseEntity<DailyRevenueResponse> dailyRevenue(
            @RequestParam("startDay") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDay,
            @RequestParam("endDay") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDay
    ) {

        List<DailyRevenueData> data = orderDao.getDailyRevenueData(startDay, endDay);
        double total = data.stream()
                .mapToDouble(DailyRevenueData::getValue)
                .sum();
        //Tính tỉ lệ tăng giảm của doanh thu so với giá trị trung bình:
        //Nếu doanh thu của ngày cuối cùng trong 7 ngày lớn hơn giá trị trung bình, trend là tăng.
        //Nếu doanh thu của ngày cuối cùng trong 7 ngày nhỏ hơn giá trị trung bình, trend là giảm.
        //Nếu doanh thu của ngày cuối cùng trong 7 ngày bằng giá trị trung bình, trend là ổn định.
        // Lấy dữ liệu doanh thu trong 7 ngày gần nhất
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDay);
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date sevenDaysAgo = calendar.getTime();
        List<DailyRevenueData> sevenDaysData = orderDao.getDailyRevenueData(sevenDaysAgo, endDay);

        // Tính giá trị trung bình của doanh thu trong 7 ngày này
        double averageRevenue = sevenDaysData.stream()
                .mapToDouble(DailyRevenueData::getValue)
                .average()
                .orElse(0.0);

        // Tính trend
        int trend = 0; //
        if (!data.isEmpty()) {
            double lastDayRevenue = data.get(data.size() - 1).getValue();
            if (lastDayRevenue > averageRevenue) {
                trend = 1; // tang
            } else if (lastDayRevenue < averageRevenue) {
                trend = -1; // giam
            }

        }
        DailyRevenueResponse response = new DailyRevenueResponse(data, trend, total);

        // Return the calculated revenue with HTTP status 200 (OK)
        return ResponseEntity.ok(response);
    }


        @GetMapping("/dailyOrders")
        public ResponseEntity<DailyOrderResponse> dailyOrder(
                @RequestParam("startDay") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDay,
                @RequestParam("endDay") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDay) {
            List<DailyOrderData> data = orderDao.getDailyOrder(startDay, endDay);
            long total = data.stream()
                    .mapToLong(DailyOrderData::getValue)
                    .sum();

            int trend = 0;
            if (data.isEmpty()) {
                trend = 0; // Không có dữ liệu, xu hướng là ổn định
            } else if (data.size() == 1) {
                trend = 0; // Chỉ có một ngày, xu hướng là ổn định
            } else {
                long firstDayValue = data.get(0).getValue();
                long lastDayValue = data.get(data.size() - 1).getValue();

                if (firstDayValue < lastDayValue) {
                    trend = 1; // Xu hướng tăng
                } else if (firstDayValue > lastDayValue) {
                    trend = -1; // Xu hướng giảm
                } else {
                    trend = 1; // Xu hướng ổn định
                }
            }

            DailyOrderResponse responses = new DailyOrderResponse(data, trend, total);

            // Trả về doanh thu đã tính với mã trạng thái HTTP 200 (OK)
            return ResponseEntity.ok(responses);
        }

    @GetMapping("/newCustomers")
    public ResponseEntity<NewCustomeDataResponse> c(
            @RequestParam("startDay") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDay,
            @RequestParam("endDay") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDay
    ) {

        List<NewCustomData>  data = usersDao.findByDateBetween(startDay, endDay);
        long total = data.stream()
                .mapToLong(NewCustomData::getValue)
                .sum();
        //Tính tỉ lệ tăng giảm của doanh thu so với giá trị trung bình:
        //Nếu doanh thu của ngày cuối cùng trong 7 ngày lớn hơn giá trị trung bình, trend là tăng.
        //Nếu doanh thu của ngày cuối cùng trong 7 ngày nhỏ hơn giá trị trung bình, trend là giảm.
        //Nếu doanh thu của ngày cuối cùng trong 7 ngày bằng giá trị trung bình, trend là ổn định.
        // Lấy dữ liệu doanh thu trong 7 ngày gần nhất
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDay);
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date sevenDaysAgo = calendar.getTime();
        List<NewCustomData> sevenDaysData = usersDao.findByDateBetween(startDay, endDay);

        // Tính giá trị trung bình của doanh thu trong 7 ngày này
        double averageRevenue = sevenDaysData.stream()
                .mapToLong(NewCustomData::getValue)
                .average()
                .orElse(0.0);

        // Tính trend
        int trend = 0; //
        if (!data.isEmpty()) {
            double lastDayRevenue = data.get(data.size() - 1).getValue();
            if (lastDayRevenue > averageRevenue) {
                trend = 1; // tang
            } else if (lastDayRevenue < averageRevenue) {
                trend = -1; // giam
            }

        }
        NewCustomeDataResponse response = new NewCustomeDataResponse(data, trend, total);

        // Return the calculated revenue with HTTP status 200 (OK)
        return ResponseEntity.ok(response);
    }
    }