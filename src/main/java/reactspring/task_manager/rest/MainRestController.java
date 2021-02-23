package reactspring.task_manager.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactspring.task_manager.entities.CardTasks;
import reactspring.task_manager.entities.Cards;
import reactspring.task_manager.services.CardService;

import java.sql.Timestamp;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api")
public class MainRestController {

    @Autowired
    private CardService cardService;

    @GetMapping(value = "/allcards")
    public ResponseEntity<?> getAllCards(){
        List<Cards> cards = cardService.getAllCards();

        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @PostMapping(value = "/addcard")
    public ResponseEntity<?> addCard(@RequestBody Cards card){
        card.setAddedDate(new Timestamp(System.currentTimeMillis()));
        cardService.addCard(card);
        return ResponseEntity.ok(card);
    }

    @GetMapping(value = "/card/{id}")
    public ResponseEntity<?> getCard(@PathVariable(name = "id") Long id){
        if(id != null){

            Cards card = cardService.getCardById(id);
            if(card != null){
                List<CardTasks> cardTasks = cardService.getCardTasksByCard(card);
                return new ResponseEntity<>(card, HttpStatus.OK);
            }

        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/card_tasks/{id}")
    public ResponseEntity<?> getTasks(@PathVariable(name = "id") Long id){
        if(id != null){

            Cards card = cardService.getCardById(id);
            if(card != null){
                List<CardTasks> cardTasks = cardService.getCardTasksByCard(card);
                return new ResponseEntity<>(cardTasks, HttpStatus.OK);
            }

        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/addtask/{id}")
    public ResponseEntity<?> addTask(@PathVariable(name = "id") Long cardId,
                                     @RequestBody CardTasks task){
        System.out.println(task.getTaskText());
        if(cardId!=null){
            Cards card = cardService.getCardById(cardId);

            if(card != null){
                if(task.getId()==null){
                    System.out.println("New task");

                    task.setAddedDate(new Timestamp(System.currentTimeMillis()));
                    task.setCard(card);
                    cardService.addCardTask(task);
                    return ResponseEntity.ok(task);
                }
                else {
                    System.out.println("existed task");
                    CardTasks existedTask = cardService.getTask(task.getId());
                    existedTask.setDone(task.isDone());
                    cardService.addCardTask(existedTask);
                    return ResponseEntity.ok(existedTask);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/deletecard/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable(name = "id") Long cardId){

        if(cardId!=null){
            Cards card = cardService.getCardById(cardId);

            if(card!=null){
                List<CardTasks> tasks = cardService.getCardTasksByCard(card);
                if(tasks!=null){
                    for(CardTasks t : tasks){
                        cardService.deleteCardTask(t);
                    }
                }
                cardService.deleteCard(card);

                return ResponseEntity.ok(card);
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
