import 'package:flutter/material.dart';
import 'package:jumpmaze/tutorial_activity1.dart';

class TutorialActivity2 extends StatefulWidget {
  const TutorialActivity2({super.key});

  @override
  State<TutorialActivity2> createState() => _TutorialActivity2State();
}

class _TutorialActivity2State extends State<TutorialActivity2> {
  late IconButton homeButton;
  late IconButton backButton;
  late IconButton nextButton;

  static const int thisPage = 2;
  int nextPage = 2;
  late Maze maze;

  @override
  void initState() {
    super.initState();
    maze = Maze.getInstance();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Tutorial Page $thisPage'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            homeButton = IconButton(
              id: 'home',
              onPressed: () {
                // TODO: update MyActivity
                // toHomePage();
              },
            ),
            backButton = IconButton(
              id: 'back_tutorial',
              onPressed: () {
                setState(() {
                  nextPage--;
                  toPage();
                });
              },
            ),
            nextButton = IconButton(
              id: 'next_tutorial',
              onPressed: () {
                setState(() {
                  nextPage++;
                  toPage();
                });
              },
            ),
          ],
        ),
      ),
    );
  }

  void toPage() {
    if (nextPage < thisPage) {
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => const TutorialActivity1()),
      );
    } else if (nextPage > thisPage) {
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => TutorialActivity3()),
      );
    }
  }
}