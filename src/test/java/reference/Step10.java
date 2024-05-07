package reference;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.tddstudy.repository.User;
import org.tddstudy.repository.Users;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class Step10 {
    @Test
    @DisplayName("10. 메서드 호출할 때마다 다른 값을 리턴하도록 세팅하기")
    public void test() {

        /*
        이전에 when .. thenReturn 을 통해 특정 메소드 호출시 반환값을 생성했다.
        이를 when..thenReturn...thenReturn.. 처럼 연쇄적으로 사용하면 호출시마다 다른 반환값을 세팅할 수 있다.
        예) 처음호출에는 true , 그다음 호출에는 false 등
         */

        List<User> userList = new ArrayList<>();
        userList.add(new User("AAAAA" , "pass123" , "내이름은A" , "010-1111-1111"));
        userList.add(new User("BBBBB" , "pass456" , "내이름은B" , "010-2222-2222"));
        userList.add(new User("CCCCC" , "pass789" , "내이름은C" , "010-3333-3333"));

        Users users = mock(Users.class); //테스트할 대상 목킹 (★★★ mocking 해야 when .. then 등 처리 가능함)

        //만약 users.findById("스트링값") 이 호출될 경우 3번호출까지는 순서대로 AAAAA , BBBBB , CCCCC 정보 호출 , 이후에는 Throw 처리
        when(users.findById(anyString()))
                .thenReturn(userList.get(0))
                .thenReturn(userList.get(1))
                .thenReturn(userList.get(2))
                .thenThrow(new RuntimeException("끝"));

        System.out.println(users.findById("첫번째호출").getName()); //내이름은A
        System.out.println(users.findById("두번째호출").getName()); //내이름은B
        System.out.println(users.findById("세번째호출").getName()); //내이름은C
        try {
            System.out.println(users.findById("네번째호출").getName());
        } catch (RuntimeException e) {

            System.out.println("네번째는 에러군요");
        }

        //혹은 다음과 같이 축약도 가능하다
        when(users.findById(anyString()))
                .thenReturn(userList.get(0) , userList.get(1) , userList.get(2))
                .thenThrow(new RuntimeException("끝"));

        System.out.println(users.findById("첫번째호출").getName()); //내이름은A
        System.out.println(users.findById("두번째호출").getName()); //내이름은B
        System.out.println(users.findById("세번째호출").getName()); //내이름은C
        try {
            System.out.println(users.findById("네번째호출").getName());
        } catch (RuntimeException ex) {
            System.out.println("네번째는 에러군요");
        }
    }

    @Test
    @DisplayName("10. 메서드 호출할 때마다 다른 값을 리턴하도록 세팅하기")
    public void test2() {

        List<User> userList = new ArrayList<>();
        userList.add(new User("AAAAA" , "pass123" , "내이름은A" , "010-1111-1111"));
        userList.add(new User("BBBBB" , "pass456" , "내이름은B" , "010-2222-2222"));
        userList.add(new User("CCCCC" , "pass789" , "내이름은C" , "010-3333-3333"));

        Users users = mock(Users.class); //테스트할 대상 목킹 (★★★ mocking 해야 when .. then 등 처리 가능함)

        //혹은 다음과 같이 축약도 가능하다
        when(users.findById(anyString()))
                .thenReturn(userList.get(0) , userList.get(1) , userList.get(2))
                .thenThrow(new RuntimeException("끝"));

        System.out.println(users.findById("첫번째호출").getName()); //내이름은A
        System.out.println(users.findById("두번째호출").getName()); //내이름은B
        System.out.println(users.findById("세번째호출").getName()); //내이름은C
        try {
            System.out.println(users.findById("네번째호출").getName());
        } catch (RuntimeException ex) {
            System.out.println("네번째는 에러군요");
        }
    }
}
