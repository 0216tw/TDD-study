package reference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tddstudy.repository.User;
import org.tddstudy.repository.Users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;


public class Step11 {
    @Mock
    TestInterface testInterface;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("11. 메서드 호출시 콜백에 특정 동작을 추가하기 doAnswer")
    public void test() {

        /*
        특정 메소드를 호출할 경우(when) 단순히 thenReturn , thenThrow 만 하는게 아니라
        특정 동작을 구현할 수 있다. (doAnswer 등을 활용)
         */

        // 예) 입력한 값의 3배 값을 리턴하는 메서드를 구현해보자
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            Object mock = invocation.getMock();
            System.out.println("인자값 확인 " + Arrays.toString(args));
            System.out.println("호출한 mock 객체" + mock);

            return (Integer)args[0] * 3; //계산 처리된 동적 리턴값 세팅

        }).when(testInterface).tripleNumber(anyInt());

        System.out.println(testInterface.tripleNumber(5)); //15
        System.out.println(testInterface.tripleNumber(10)); //30
        System.out.println(testInterface.tripleNumber(500)); //100

    }

    interface TestInterface {
        int tripleNumber(int number);
    }
}
