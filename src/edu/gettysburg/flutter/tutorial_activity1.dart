import 'package:flutter/material.dart';
import 'package:jumpmaze/tutorial_activity2.dart';

class TutorialActivity1 extends StatefulWidget {
  const TutorialActivity1({super.key});

  @override
  State<TutorialActivity1> createState() => _TutorialActivity1State();
}

class _TutorialActivity1State extends State<TutorialActivity1> {
  late IconButton homeButton;
  late IconButton backButton;
  late IconButton nextButton;

  static const int thisPage = 1;
  int nextPage = 1;
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
      // TODO: update MyActivity
      // toHomePage();
    } else if (nextPage > thisPage) {
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => const TutorialActivity2()),
      );
    }
  }
}
