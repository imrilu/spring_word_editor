package com.tomiaglobal.hw;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordEditorController {

    private static WordEditor wordEditor = new WordEditor();

    @GetMapping("/add")
    @ResponseBody
    public String add(@RequestParam(value = "s") String s,
                           @RequestParam(value = "position", defaultValue = "-1") int position) {
        if (position == -1)
            wordEditor.add(s);
        else
            wordEditor.add(s, position);
        return wordEditor.print();
    }

    @GetMapping("/remove")
    @ResponseBody
    public String remove(@RequestParam(value = "fromPosition") int fromPosition,
                         @RequestParam(value = "toPosition") int toPosition) {
        wordEditor.remove(fromPosition, toPosition);
        return wordEditor.print();
    }

    @GetMapping("/italic")
    @ResponseBody
    public String italic(@RequestParam(value = "fromPosition") int fromPosition,
                         @RequestParam(value = "toPosition") int toPosition) {
        wordEditor.italic(fromPosition, toPosition);
        return wordEditor.print();
    }

    @GetMapping("/bold")
    @ResponseBody
    public String bold(@RequestParam(value = "fromPosition") int fromPosition,
                         @RequestParam(value = "toPosition") int toPosition) {
        wordEditor.bold(fromPosition, toPosition);
        return wordEditor.print();
    }

    @GetMapping("/underline")
    @ResponseBody
    public String underline(@RequestParam(value = "fromPosition") int fromPosition,
                            @RequestParam(value = "toPosition") int toPosition) {
        wordEditor.underline(fromPosition, toPosition);
        return wordEditor.print();
    }

    @GetMapping("/redo")
    @ResponseBody
    public String redo() {
        wordEditor.redo();
        return wordEditor.print();
    }

    @GetMapping("/undo")
    @ResponseBody
    public String undo() {
        wordEditor.undo();
        return wordEditor.print();
    }

    @GetMapping("/print")
    @ResponseBody
    public String print() {
        return wordEditor.print();
    }



}