package algo;

import redcode.battle.*;

import java.util.Random;

import generateur.*;
import memory.InstructionBoard;
import redcode.parser.*;
import warior.Warior;
import redcode.instruction.*;
import java.util.*;
import graphique.mvc.*;

public class SimulatedAnnealing extends AbstractModeleEcoutable {
    public int N_VALIDE_MUTATION;
    public static int NOMBRE_DE_MATCH = 14;
    private String[] allValideMutation;
    private double[] mutationResult;
    private int nValideMutation;
    public final double INIT_TEMPERATURE;
    private String code;
    private int length;
    private double temperature;
    private Random random = new Random(System.currentTimeMillis());
    private String initCode;

    public SimulatedAnnealing(String script, boolean isScript, int nvalideMutation) {
        if (isScript) {
            this.code = RedCodeParser.scriptToString("code/" + script);
        } else {
            this.code = script;
        }
        N_VALIDE_MUTATION = nvalideMutation;
        this.allValideMutation = new String[N_VALIDE_MUTATION];
        this.mutationResult = new double[N_VALIDE_MUTATION];
        this.length = (RedCodeParser.treatCode(this.code)).size();
        this.initCode = "NOP $2 $2";
        INIT_TEMPERATURE = 1 - (getObjectifValue(this.code) / (14 + (196 / 2) + (5 * 14)));
        this.initCode = this.code;
        this.temperature = INIT_TEMPERATURE;
    }

    public SimulatedAnnealing(int nvalideMutation) {
        this("MOV $7 $-1\nSUB #1 $-1\nADD #1 $3\nCMP #1 $2\nJMI $-4 $2\nSPL $3 $0\nJMI $-2 $2\nDAT #1363 #0\nMOV $3 $4\nADD #1 $-1\nJMI $-2 $3\nDAT #0 #0",
                false, nvalideMutation);
    }

    public int[] simulerCombat(String code1, String code2) {
        Battle b = null;

        int nVictoire = 0;
        int nLose = 0;
        int nEgalite = 0;

        int ordre1 = NOMBRE_DE_MATCH / 2;
        int ordre2 = (NOMBRE_DE_MATCH % 2 == 0) ? ordre1 : ordre1 + 1;

        for (int i = 0; i < ordre1; i++) {
            b = new Battle(code1, code2, CodeMode.ONLY_STRING);
            switch (b.startBattle()) {
                case -1:
                    nEgalite += 1;
                    break;
                case 1:
                    nVictoire += 1;
                    break;
                case 2:
                    nLose += 1;
                    break;
            }
        }

        for (int i = 0; i < ordre2; i++) {
            b = new Battle(code2, code1, CodeMode.ONLY_STRING);
            switch (b.startBattle()) {
                case -1:
                    nEgalite += 1;
                    break;
                case 2:
                    nVictoire += 1;
                    break;
                case 1:
                    nLose += 1;
                    break;
            }
        }

        int[] tab = { nVictoire, nLose, nEgalite };
        return tab;
    }

    public int objectifSurvie(String solution) {
        int rate = 0;
        Battle b = new Battle(solution, "NOP $0 $0", CodeMode.ONLY_STRING);
        int result = b.startBattle();
        if (result == -1) {
            rate += 2;
        } else if (result == 1) {
            rate += 7;
        }
        Battle b2 = new Battle("NOP $0 $0", solution, CodeMode.ONLY_STRING);
        int resultb2 = b2.startBattle();
        if (resultb2 == -1) {
            rate += 2;
        } else if (resultb2 == 2) {
            rate += 7;
        }
        return rate;
    }

    public double objectifCombat(String solution) {
        double rate = 0;
        for (int i = 0; i < 14; i++) {
            int[] result = simulerCombat(solution, Generateur.generateValideCode(length));
            rate += result[0] * 0.5;
            rate += result[2] * 0.1;
        }
        int[] result = simulerCombat(solution, initCode);
        rate += result[0] * 5;
        rate += result[2] * 2;
        return rate;
    }

    public double getObjectifValue(String solution) {
        return objectifCombat(solution) + objectifSurvie(solution);
    }

    public double getTemperature() {
        return this.temperature;
    }

    private String swapMode(ArrayList<Instruction> instructions) {
        if ((instructions.size()) < 2) {
            return "invalideOperation";
        } else {
            int index1 = (int) (Math.random() * instructions.size());
            int index2 = (int) (Math.random() * instructions.size());

            while (index1 == index2) {
                index1 = (int) (Math.random() * instructions.size());
            }

            Collections.swap(instructions, index1, index2);
            return RedCodeParser.instructionToString(instructions);
        }
    }

    private String changeSizeMode(ArrayList<Instruction> instructions) {
        int n = random.nextInt(2) + 1;
        if (n == 1) {
            if (instructions.size() + 1 >= Warior.MAX_INIT_WARIOR_SIZE) {
                return "invalideOperation";
            } else {
                instructions.add(RedCodeParser.treatLine(Generateur.generateLine()));
                return RedCodeParser.instructionToString(instructions);
            }
        } else {
            if (instructions.size() <= 1) {
                return "invalideOperation";
            } else {
                instructions.remove(random.nextInt(instructions.size()));
                return RedCodeParser.instructionToString(instructions);
            }
        }

    }

    private String changeOperandMode(ArrayList<Instruction> instructions) {
        if (instructions.size() < 1) {
            return "invalideOperation";
        } else {
            HashMap<String, String[]> dico = RedCodeParser.getAddressingMode();
            int nLine = random.nextInt(instructions.size());
            int n = random.nextInt(2) + 1;
            String opCode = (instructions.get(nLine)).getOpCode();
            String[] operandsMode = dico.get(opCode);

            if (n == 1) {
                String operandAMode = (instructions.get(nLine)).getOperandAMode();
                char newOperandAMode = (operandsMode[0]).charAt(random.nextInt((operandsMode[0]).length()));

                while (operandAMode.equals(Character.toString(newOperandAMode))) {
                    newOperandAMode = (operandsMode[0]).charAt(random.nextInt((operandsMode[0]).length()));
                }
                (instructions.get(nLine)).setOperandAMode(Character.toString(newOperandAMode));
                return RedCodeParser.instructionToString(instructions);
            } else {
                String operandBMode = (instructions.get(nLine)).getOperandBMode();
                char newOperandBMode = (operandsMode[1]).charAt(random.nextInt((operandsMode[1]).length()));

                while (operandBMode.equals(Character.toString(newOperandBMode))) {
                    newOperandBMode = (operandsMode[1]).charAt(random.nextInt((operandsMode[1]).length()));
                }

                (instructions.get(nLine)).setOperandBMode(Character.toString(newOperandBMode));
                return RedCodeParser.instructionToString(instructions);
            }
        }
    }

    private String changeOperand(ArrayList<Instruction> instructions) {
        if (instructions.size() < 1) {
            return "invalideOperation";
        } else {
            int nLine = random.nextInt(instructions.size());
            int n = random.nextInt(2) + 1;

            if (n == 1) {
                int operandA = (instructions.get(nLine)).getOperandA();
                int newOperandA = random.nextInt(InstructionBoard.SIZE_X * InstructionBoard.SIZE_Y);

                while (operandA == newOperandA) {
                    newOperandA = random.nextInt(InstructionBoard.SIZE_X * InstructionBoard.SIZE_Y);
                }

                (instructions.get(nLine)).setOperandA(newOperandA);
                return RedCodeParser.instructionToString(instructions);
            } else {
                int OperandB = (instructions.get(nLine)).getOperandB();
                int newOperandB = random.nextInt(InstructionBoard.SIZE_X * InstructionBoard.SIZE_Y);

                while (OperandB == newOperandB) {
                    newOperandB = random.nextInt(InstructionBoard.SIZE_X * InstructionBoard.SIZE_Y);
                }

                (instructions.get(nLine)).setOperandB(newOperandB);
                return RedCodeParser.instructionToString(instructions);
            }
        }
    }

    public int getRandomMode() {
        double rand = random.nextDouble();
        if (rand < 0.3) {
            return 1;
        } else if (rand < 0.6) {
            return 2;
        } else if (rand < 0.8) {
            return 3;
        } else {
            return 4;
        }
    }

    public String mutation(String code) {
        int mutationMode = getRandomMode();
        ArrayList<Instruction> instructions = RedCodeParser.treatCode(code);
        switch (mutationMode) {
            case 1:
                return (changeOperand(instructions));

            case 2:
                return (changeOperandMode(instructions));

            case 3:
                return (changeSizeMode(instructions));
            default:
                return (swapMode(instructions));
        }
    }

    public boolean isBetter(String code1, String code2) {
        int[] result = simulerCombat(code1, code2);
        return ((result[0] > result[1]) && result[1] != NOMBRE_DE_MATCH);
    }

    public void startAlgo() {
        nValideMutation = 0;
        boolean alreadyChanged = false;
        while (nValideMutation < N_VALIDE_MUTATION) {
            String actualMutation = null;
            do {
                actualMutation = mutation(this.code);
            } while (contient(allValideMutation, actualMutation) || actualMutation.equals("invalideOperation"));
            if (isBetter(actualMutation, this.code)) {
                this.code = actualMutation;
                allValideMutation[nValideMutation] = actualMutation;
                mutationResult[nValideMutation] = getObjectifValue(actualMutation);
                nValideMutation++;
                alreadyChanged = false;
                fireChangement();
            } else {
                double rand = this.random.nextDouble();
                if (rand < this.temperature) {
                    this.code = actualMutation;
                    allValideMutation[nValideMutation] = actualMutation;
                    mutationResult[nValideMutation] = getObjectifValue(actualMutation);
                    nValideMutation++;
                    alreadyChanged = false;
                    fireChangement();
                }
            }

            boolean t1 = nValideMutation == (int) ((N_VALIDE_MUTATION / 8) * 1);
            boolean t2 = nValideMutation == (int) ((N_VALIDE_MUTATION / 8) * 2);
            boolean t3 = nValideMutation == (int) ((N_VALIDE_MUTATION / 8) * 3);
            boolean t4 = nValideMutation == (int) ((N_VALIDE_MUTATION / 8) * 4);
            boolean t5 = nValideMutation == (int) ((N_VALIDE_MUTATION / 8) * 5);
            boolean t6 = nValideMutation == (int) ((N_VALIDE_MUTATION / 8) * 6);
            boolean t7 = nValideMutation == (int) ((N_VALIDE_MUTATION / 8) * 7);

            boolean changeTemperature = t1 || t2 || t3 || t4 || t5 || t6 || t7;

            if (changeTemperature && (!alreadyChanged)) {
                fireChangement();
                alreadyChanged = true;
                this.temperature -= (INIT_TEMPERATURE / 8);
            }
        }
    }

    public static boolean contient(String[] tableau, String valeur) {
        for (int i = 0; i < tableau.length; i++) {
            if (tableau[i] == valeur) {
                return true;
            }
        }
        return false;
    }

    public int getNValideMutation() {
        return (this.nValideMutation);
    }

    public String getBestCode() {
        startAlgo();
        double max = Integer.MIN_VALUE;
        int maxRowIndex = -1;

        for (int i = 0; i < this.mutationResult.length; i++) {
            if (this.mutationResult[i] > max) {
                max = this.mutationResult[i];
                maxRowIndex = i;
            }
        }
        return this.allValideMutation[maxRowIndex];
    }
}