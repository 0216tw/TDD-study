package reference;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.tddstudy.repository.User;
import org.tddstudy.repository.Users;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class Step3 {
    @Test
    @DisplayName("3. anyString() , argThat 등 사용해보기")
    public void argumentMatchers() {

        List<User> userList = new ArrayList<>();
        userList.add(new User("AAAAA" , "pass123" , "내이름은A" , "010-1111-1111"));
        userList.add(new User("BBBBB" , "pass456" , "내이름은B" , "010-2222-2222"));
        userList.add(new User("CCCCC" , "pass789" , "내이름은C" , "010-3333-3333"));

        Users users = mock(Users.class); //테스트할 대상 목킹 (★★★ mocking 해야 when .. then 등 처리 가능함)
        users.setUsers(userList);

        /* anyString() : 입력값으로 아무 "String" 값이 들어오면 userList.get(0) 을 반환하겠다.
        *                Step2와 달리 특정 값을 지정하는게 아니라 any 값을 줄 수 있다. anyInt , anyString 등.. */
        when(users.findById(anyString())).thenReturn(userList.get(0));

        /* hasPasswordDollorWord(id ,pw) : id가 존재하는 사용자의 패스워드에 $(달러) 특수문자가 있으면 true를 리턴하는 메서드
           argThat : 입력하는 argument의 값에 대한 조건을 줄 수 있다.

                      예) 입력받는 값 중에 id 에 A 가 포함이 되어있고, pw에 #(원래는 $이지만 테스트니까) 기호가 있다면 true를 리턴
         */
            when(users.hasPasswordDollarWord(ArgumentMatchers.argThat(u -> u.contains("A")) ,
                    ArgumentMatchers.argThat(pw -> pw.contains("#")))).thenReturn(true);
        /*
            ★★★  argument Matcher 사용시 주의사항!
            세팅하는 인자가 모두 matcher 로 세팅이 되어야 한다.
            또한 matcher 는 verify , stub 메서드 내부에서만 쓸 수 있다. (외부에서는 사용불가능)
            예를 들어
            verify(mock).someMethod(anyInt() , anyString() , eq("3번째인자") ); 는 모두 argumentMatcher 로 주어졌으니 가능하다.

            하지만
            verify(mock).someMethod(anyInt() , anyString() , "3번째인자" ); 에서 "3번째인자" 는 matcher 가 아니므로 예외가 발생한다.
         */


        //호출
        User foundUser = users.findById("11122"); //인자로 11122를 넣었으나, 위 stub으로 인해 userList.get(0) 의 User가 나옴
        System.out.println("[FOUNDED USERID]" + foundUser.getId()); // AAAAA

        //만약 ID에 A가 포함되고 패스워드에 #가 포함된다면 true 반환
        boolean isHasPasswordDollarWord = users.hasPasswordDollarWord(foundUser.getId() , "#123123");  // id : AAAAA , pw : #123123

        verify(users , times(1)).findById(anyString()); //true
        Assertions.assertEquals( true , isHasPasswordDollarWord);     //패스워드에 #가 있었기에 true리턴

    }
}
