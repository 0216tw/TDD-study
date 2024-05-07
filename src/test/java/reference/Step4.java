package reference;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.tddstudy.repository.User;
import org.tddstudy.repository.Users;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class Step4 {
    @Test
    @DisplayName("4. 메서드 호출 횟수 확인하기 ")
    public void test() {

        List<User> userList = new ArrayList<>();
        userList.add(new User("AAAAA" , "pass123" , "내이름은A" , "010-1111-1111"));
        userList.add(new User("BBBBB" , "pass456" , "내이름은B" , "010-2222-2222"));
        userList.add(new User("CCCCC" , "pass789" , "내이름은C" , "010-3333-3333"));

        Users users = mock(Users.class); //테스트할 대상 목킹 (★★★ mocking 해야 when .. then 등 처리 가능함)
        users.setUsers(userList);

        // findById() 2회 호출
        users.findById(anyString());
        users.findById(anyString());

        // hasPasswordDollarWord() 3회 호출
        users.hasPasswordDollarWord(anyString(),anyString()) ;
        users.hasPasswordDollarWord(anyString(),anyString()) ;
        users.hasPasswordDollarWord(anyString(),anyString()) ;

        // isValid() 호출 안함 (0회)
        // users.isValid();

        /*
        호출 횟수 테스트 [
        times(n) : 정확히 n번 호출되었는가?
        never()  : 한번도 호출 되지 않았는가? or 0번 호출되었는가?
        atMostOnce() : 많아야 한번 호출되었는가? (0~1)
        atLeastOnce() : 최소 한번 호출되었는가? (1~ )
        atLeast(n) :    최소 n번 호출 되었는가 (n~)
        atMost(n) ] : 많아야 n번 호출 되었는가? (0~n)
        */

        //verify(users , times(1)).findById(anyString()); // error (but found 2)
        verify(users , times(2)).findById(anyString()); // 통과

        verify(users , times(3)).hasPasswordDollarWord(anyString(), anyString()); //통과
        // verify(users , never()).hasPasswordDollarWord(anyString(), anyString()); //error (never 이지만 위 @@줄에서 호출되었습니다)

        verify(users , never()).isValid(); //통과
        //verify(users , atMostOnce()).findById(anyString()); // 에러 (많아야 한번인데 findById는 2회 출력됨)
        verify(users , atMost(2)).findById(anyString()); // 통과

        verify(users , atLeastOnce()).hasPasswordDollarWord(anyString(), anyString()); //통과
        verify(users , atLeast(2)).hasPasswordDollarWord(anyString(), anyString()); //통과



    }
}
