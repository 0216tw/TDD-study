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


public class Step12 {
    @Mock
    TestInterface testInterface;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("12. 콜백으로 doAnswer 외에 다른 것들도 사용해보자.")
    public void test() {

        /*
        종류 :
        doReturn() :
        when..thenReturn이 가독성이 더 좋으나, 특정 상황에서는 doReturn이 더 좋다고 한다.
        예) Spy가 적용된 실제 리스트가 만약 비어있는 상황에서, get(0)을 하면 IndexOutOfBoundsExcpetion이 발생할 것이다.
        이 경우 when(spy.get(0)).thenReturn("foo") 에서 spy는 실제 객체의 메서드를 호출하니까 위 예외가 발생한다.
        반대로 doReturn("foo").when(spy).get(0) 은 실제 메서드를 호출하지 않고 return을 할 수 가 있다.
        하지만 doReturn은 타입안정성도 좋지않기에 드물게 사용을 하는 편이다.

        doThrow() :
        만약 void 반환인 메서드에 대해 예외를 세팅하고 싶을때 사용할 수 있다.
        예) doThrow(예외객체).when(mock객체).반환이없는메소드();

        doAnswer() : step11참고

        doNothing() : 아무작업도 하지 않게 할 수 있다.
        예)
        (1) 연쇄 처리
        doNothing().doThrow(new RuntimeException()).when(mock).someVoidMethod(); -> 메서드호출시 처음에는 아무것도 하지말고 , 두번째는 예외를 던짐

        (2) spy객체에 대해 처리를 안하게 함
        List spy = spy(list) 인경우 이 spy는 실제 list객체가 가진 메서드를 수행한다.
        만약 doNothing().when(spy).clear() 를 하게 되면, 원래는 clear()로 내부 엘리먼트를 모두 없애야 하지만 doNothing()이 있으니 clear를 무시한다.

        doCallRealMethod() :
        mock객체는 기본적으로 실제 메서드를 실행하지 않는다.
        하지만 위 doCallRealMethod()를 호출하게 되면 특정 메서드에 대해서 실제 구현을 처리할 수 있다.

        근데 spy를 쓰라고 권장하는 듯 하다. (spy도 실제 객체의 메서드를 사용하되, 부분적으로 mocking 처리가 가능하니까!)

           ....

           void 반환값이 메서드만 가능한가보다

         */


        ///////////////////// doAnswer 테스트 /////////////////////

        // 예) 입력한 값의 3배 값을 리턴하는 메서드를 구현해보자
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return (Integer)args[0] * 3; //계산 처리된 동적 리턴값 세팅
        }).when(testInterface).tripleNumber(anyInt());

        System.out.println("doAnswer 테스트 ===========");
        System.out.println(testInterface.tripleNumber(500)); //100
        System.out.println("doAnswer 테스트 ===========");
        System.out.println();


        ///////////////////// doReturn 테스트 /////////////////////

        doReturn(-1).when(testInterface).tripleNumber(2147482157); //3배면 int의 범위를 넘어섬

        System.out.println("doReturn 테스트 ===========");
        System.out.println(testInterface.tripleNumber(2147482157));
        System.out.println("doReturn 테스트 ===========");
        System.out.println();


        ///////////////////// toThrow 테스트 //////////////////////
        doThrow(new RuntimeException() , new ArithmeticException()).when(testInterface).tripleNumber(2); //반환형이 int라 안먹힘
        System.out.println(testInterface.tripleNumber(5)); //15

        doThrow(new RuntimeException() , new ArithmeticException()).when(testInterface).clear();


        System.out.println("doThrow 테스트 ===========");
        try {
            testInterface.clear();
        } catch(RuntimeException e) {
            System.out.println("런타임 걸리셨구요");

            try {
                testInterface.clear();
            } catch(ArithmeticException ex) {
                System.out.println("Arith 걸리셨습니다.");
                System.out.println("doThrow 테스트 ===========");
                System.out.println();
            }
        }

        ///////////////////// doNothing 테스트 //////////////////////
        doNothing().when(testInterface).somethingPrint("hihi"); ///원래는 hihi를 출력했어요 가 나와야하지만 아무것도 못하게 함

        System.out.println("doNothing 테스트 ===========");
        testInterface.somethingPrint("hihi");
        System.out.println("doNothing 테스트 ===========");
        System.out.println();

        ///////////////////// doCallRealMethod 테스트 //////////////////////

        List<User> userList = new ArrayList<>();
        userList.add(new User("AAAAA" , "pass123" , "내이름은A" , "010-1111-1111"));
        userList.add(new User("BBBBB" , "pass456" , "내이름은B" , "010-2222-2222"));
        userList.add(new User("CCCCC" , "pass789" , "내이름은C" , "010-3333-3333"));

        Users users = mock(Users.class);
        doCallRealMethod().when(users).setUsers(userList); //setUsers 호출시 실제 메서드를 호출 실행해라 (원래 mock는 이거 실행해도 호출만! 하지 처리는 안되었음)
        doCallRealMethod().when(users).findById("AAAAA");  //이 경우도 만약 findById("AAAAA") 호출시 실제 메서드를 실행시켜라 라는 의미

        System.out.println("doNothing 테스트 ===========");
        users.setUsers(userList);
        System.out.println("AAAAA id의 이름은 => " + users.findById("AAAAA").getName());
        System.out.println("doNothing 테스트 ===========");
        System.out.println();

    }

    interface TestInterface {
        int tripleNumber(int number);
        void clear();

        default void somethingPrint(String print) {
            System.out.println(print+"를 출력했어요");
        }
    }
}
